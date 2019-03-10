package action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction

class GenerateCopyWithMethodAction : AnAction("Generate copyWith") {

    override fun update(event: AnActionEvent) {
        val project = event.project
        val editor = event.getData(CommonDataKeys.EDITOR)
        event.presentation.isVisible = project != null && editor != null
    }

    override fun actionPerformed(event: AnActionEvent) {
        val editor = event.getData(CommonDataKeys.EDITOR) ?: return
        val toInsert = CopyWithMethodGenerator().generate(editor.document.charsSequence) ?: return
        WriteCommandAction.runWriteCommandAction(event.project) {
            editor.document.insertString(editor.caretModel.currentCaret.offset, toInsert)
        }
    }
}
