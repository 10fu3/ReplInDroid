package jp.msfblue1.replindroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import jp.msfblue1.replindroid.lisp.eval.Environment
import jp.msfblue1.replindroid.lisp.eval.Evaluator
import jp.msfblue1.replindroid.lisp.exception.EndOfFileException
import jp.msfblue1.replindroid.lisp.reader.Reader
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.StringReader
import java.util.*

enum class Sender {
    USER("USER"),SYSTEM("SYSTEM_INFO"),SYSTEM_ERROR("SYSTEM_ERROR");

    val label:String
    constructor(label:String){
        this.label = label
    }

    override fun toString(): String {
        return this.label;
    }
}

data class History(val sender: Sender,val date:String,val content:String,val id: String = UUID.randomUUID().toString())

val themeColor = Color(0, 117, 255)

enum class TopDisplayMode(val label:String){
    REPL("REPL"),
    ENV_LIST("ENV_LIST");
    override fun toString(): String{
        return label
    }

    companion object Factory{
        private val items = listOf(REPL,ENV_LIST)
        private val itemString = items.map { it.toString() }
        private val itemMap:Map<Int,String> = itemString.mapIndexed{index: Int, mode:String -> index to mode }.toMap()

        fun getMode(modeIndex:Int):String{
            return itemMap.get(modeIndex) ?: ""
        }

        fun getAll():List<String>{
            return itemString
        }
    }
}

@Composable
fun InputForm(text:String,onChanged:(str:String)->Unit,onEnter:()->Unit){

    Box (
        Modifier
            .background(color = themeColor)
            .fillMaxWidth()
            .height(65.dp)
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Rtl,
        ){
            Row(
                modifier =
                Modifier
                    .padding(top = 11.5.dp, bottom = 4.dp, start = 12.dp, end = 4.dp)
                    .background(color = themeColor),
            ) {
                Button(
                    shape = RoundedCornerShape(5.dp),
                    modifier =
                    Modifier.height(39.dp),
                    onClick = onEnter,
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = Color.White,
                        contentColor = themeColor,
                        disabledContentColor = Color.LightGray
                    ),
                ) {
                    CompositionLocalProvider(
                        LocalLayoutDirection provides LayoutDirection.Ltr,
                    ){
                        Text(buildAnnotatedString {
                            withStyle(SpanStyle(color = themeColor)){
                                append("->")
                            }
                        })
                    }
                }
                CompositionLocalProvider(
                    LocalLayoutDirection provides LayoutDirection.Ltr,
                ){
                    BasicTextField(
                        modifier =
                        Modifier
                            .padding(start = 12.dp, end = 12.dp)
                            .height(40.dp)
                            .fillMaxWidth()
                            .imePadding(),
                        value = text,
                        onValueChange = onChanged,
                        decorationBox = { innerTextField ->
                            Box(Modifier.fillMaxWidth().background(color = Color.White, shape = RoundedCornerShape(5.dp))) {
                                Box(Modifier.fillMaxWidth().padding(top = 10.dp, start = 5.dp, end = 5.dp, bottom = 10.dp)){
                                    innerTextField()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryListItem(history: History){
    Box(Modifier.padding(top = 1.5.dp, bottom = 1.5.dp, start = 3.dp, end = 3.dp)){
        Column(
            Modifier
                .background(
                    color = when (history.sender) {
                        Sender.SYSTEM -> Color(205, 255, 207, 255)
                        Sender.SYSTEM_ERROR -> Color(255, 205, 205, 255)
                        Sender.USER -> Color(205, 219, 255, 255)
                    },
                    shape = RoundedCornerShape(10)
                )
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Text(text = history.content)
        }
    }
}

@Composable
fun DisplayREPL(env:Environment, historyList:SnapshotStateList<History>){

    val configuration = LocalConfiguration.current

    val (input,onInputChanged) = remember {
        mutableStateOf("")
    }

    val listState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    Box(contentAlignment = Alignment.BottomCenter){
        Column(modifier = Modifier.height(configuration.screenHeightDp.dp)) {
            LazyColumn(reverseLayout = true,modifier = Modifier.padding(bottom = 65.dp)){
                items(historyList.reversed(),{item:History -> item.id}){
                    HistoryListItem(history = it)
                }
            }
        }
        InputForm(text = input, onChanged = onInputChanged){
            val userInput = History(sender = Sender.USER, date = "", content = input)
            historyList.add(userInput)
            val reader = Reader(BufferedReader(StringReader(input)))
            while (true){
                try {
                    val result = History(sender = Sender.SYSTEM, date = "", content = Evaluator.eval(reader.read(),env).toString())
                    historyList.add(result)
                }catch (e:EndOfFileException){
                    break
                }catch (e:java.lang.Exception){
                    var result = History(sender = Sender.SYSTEM_ERROR, date = "", content = e.message ?: "")
                    historyList.add(result)
                    e.stackTrace
                        .filter { it.className.startsWith("jp.msfblue1.replindroid.lisp") }
                        .forEach{ err ->
                            result = History(sender = Sender.SYSTEM_ERROR, date = "", content = err.toString())
                            historyList.add(result)
                        }
                }
            }
            onInputChanged("")

            coroutineScope.launch {
                listState.animateScrollToItem(historyList.size)
            }
        }
    }
}

@Composable
fun Tab(displayMethod: @Composable (mode:String) -> Unit) {
    val themeColor = Color(0, 117, 255)

    var (tabIndex,setTabIndex) = remember{
        mutableStateOf(0)
    }

    val tabTitles = TopDisplayMode.getAll()
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[tabIndex])
                        .height(3.dp)
                        .padding(start = 20.dp, end = 20.dp)
                        .background(themeColor)
                )
            },
            selectedTabIndex = tabIndex,
            backgroundColor = Color.White) { // 3.
            tabTitles.forEachIndexed { index, title ->
                Tab(selected = tabIndex == index, // 4.
                    onClick = {
                        setTabIndex(index)
                    },
                    text = { Text(text = title,color = (if (index == tabIndex) themeColor else Color.Black)) }) // 5.
            }
        }
        displayMethod(TopDisplayMode.getMode(tabIndex))
    }
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val configuration = LocalConfiguration.current
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth().imePadding().padding(top = 46.dp).height(configuration.screenHeightDp.dp),
                    color = MaterialTheme.colors.background
                ) {
                    val mother = Environment.createGlobalEnv()

                    val list = remember { listOf<History>().toMutableStateList() }

                    Tab{
                        when (it){
                            "REPL"-> DisplayREPL(env = mother, historyList = list)
                            "ENV_LIST"-> Text(text = "")
                            ""-> Text(text = "")
                        }
                    }

                }
            }
        }
    }
}