import java.io.File
import Analyzer.Lexical
import Analyzer.Syntactic
import ViewModels.TokenViewModel

/**
 * Rules —————————————————————————————————————————————————————————————————
 * Regular expressions for these tokens in the github readme and in Lexical.kt
 * declare variables with `dec`
 * four integer types:
 * tiny : 1 byte
 * small : 2 bytes
 * mid : 4 bytes
 * big : 8 bytes
 * `being` loop = while loop
 *  lines terminated with `~`
 *
 * Production Rules ———————————————————————————————————————————————————————
 * <program> -> `start` <block>
 * <stmt> —> <fact> | <being> | <assign> | <block>
 * <block> —> `{` { <stmt> } `}`
 * <fact> —> `fact` `(`<bool_expr>`)` <stmt> [`wrong` <stmt>]
 * <being> —> `fact` `(`<bool_expr>`)` <stmt> [`wrong` <stmt>]
 * <bool_expr> -> <term> {(<|>|==) <term>}
 * <expr> -> <term> { (/|*) <term>}
 * <term> -> <factor> {(%|-|+) <factor>}
 * <factor> -> `id` | `int_literal` | (<expr>)
 * <assign> -> `id` `=` <expr>
 *
 * Precedence order ——————————————————————————————————————————————————————
 * —Math Operations—
 * Parentheses -> Modulo -> Subtraction -> Addition -> division -> multiplication
 * —Boolean Operations—
 * Parentheses -> Equal-to -> Less-than -> Greater-than
 */

fun main() {
    val viewModel = TokenViewModel()
    val lex = Lexical(viewModel)

    val fileName = "src/main/resources/test_one.txt"
    //splitter regex splits the string while keeping the delimiters
    val splitter = Regex("(?<=[{}|\\[|\\]|(|)|~|\\s])|(?=[{}|\\[|\\]|(|)|~|\\s])")

    val rawCode: String = File(fileName).readText()
    viewModel.tokenArray = rawCode.split(splitter) as MutableList<String>
    var rawTokens = viewModel.tokenArray
    var tokens: MutableList<String> = mutableListOf()

    //remove whitespace strings
    for (i in 0 until rawTokens.size-1){
        if(!Lexical.whitespace.matches(rawTokens[i])){
            tokens.add(rawTokens[i])
        }
    }
    val syn = Syntactic(tokens)
    //to see the tokens
    for(i in tokens){
        println(i)
    }

    //call lexical analyzer to count the tokens
    lex.printCount(tokens)
    syn.startProgram()

}