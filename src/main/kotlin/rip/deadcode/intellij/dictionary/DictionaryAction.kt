package rip.deadcode.intellij.dictionary

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.ui.awt.RelativePoint

class DictionaryAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val editor = FileEditorManager.getInstance(e.project!!).selectedTextEditor!!

        val popup = DictionaryComponent.createPopup(editor.selectionModel.selectedText)

        val cursorPos = editor.visualPositionToXY(editor.caretModel.visualPosition)
        val rp = RelativePoint(editor.contentComponent, cursorPos)
        popup.show(rp)
    }

}
