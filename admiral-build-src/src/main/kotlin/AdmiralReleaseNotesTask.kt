import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File

abstract class UpdateReleaseNotesTask : DefaultTask() {
    private val fileName = "demo/release-notes.txt"

    @OutputFile
    val releaseNotesFile: File = File(fileName)

    private var releaseNotes: String? = null

    @Option(
        option = "releaseNotes",
        description = "Configures the text to be written to the release notes file."
    )
    open fun setReleaseNotes(releaseNotes: String?) {
        this.releaseNotes = releaseNotes
    }

    @TaskAction
    fun action() {
        releaseNotesFile.createNewFile()
        releaseNotes?.let { text: String ->
            releaseNotesFile.writeText(text)
        }
    }
}
