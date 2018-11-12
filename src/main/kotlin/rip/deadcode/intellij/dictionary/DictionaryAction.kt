package rip.deadcode.intellij.dictionary

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.ui.awt.RelativePoint
import java.awt.Point
import java.awt.Toolkit

class DictionaryAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val project = e.project
        val editor = if (project != null) {
            FileEditorManager.getInstance(project).selectedTextEditor
        } else null

        val selection = editor?.selectionModel?.selectedText ?: ""

        val popup = DictionaryComponent.createPopup(selection)

        val rp = if (editor != null) {
            val cursorPos = editor.visualPositionToXY(editor.caretModel.visualPosition)
            RelativePoint(editor.contentComponent, cursorPos)
        } else {
            val screenSize = Toolkit.getDefaultToolkit().screenSize
            RelativePoint(Point(screenSize.width / 2 - 160, screenSize.height / 2 - 90))  // TODO
        }

        popup.show(rp)
    }

}
