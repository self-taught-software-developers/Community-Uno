import kotlinx.browser.document
import kotlinx.dom.appendElement
import kotlinx.dom.appendText
import org.w3c.dom.HTMLInputElement

fun main() {
    document.body?.appendElement("div") {
        appendText("Time in ")

        val output = document.createElement("span")

        val input = appendElement("input") {
            this as HTMLInputElement
            type = "text"
            placeholder="Timezone"

            value = "Europe/Amsterdam"

            addEventListener("change") {
//                updateTime(this, output)
            }
        } as HTMLInputElement

        appendText(" is ")
        appendChild(output)

//        updateTime(input, output)
    }
}