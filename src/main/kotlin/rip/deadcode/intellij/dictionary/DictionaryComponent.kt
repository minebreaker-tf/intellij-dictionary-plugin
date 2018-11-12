package rip.deadcode.intellij.dictionary

import com.intellij.ide.IdeTooltipManager
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.HintHint
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JComponent
import javax.swing.border.EmptyBorder
import javax.swing.border.LineBorder
import javax.swing.text.html.HTMLEditorKit

class DictionaryComponent(defaultText: String?) : JComponent() {

    internal val input = JBTextField()
    internal var hideHandler: () -> Unit = {}
    private val resultView = IdeTooltipManager.initPane("", HintHint(), null).also {
        val editorKit = it.editorKit as HTMLEditorKit
        val s = editorKit.styleSheet
        s.addRule("body { font-size: 16px; }")  // TODO cool css classes
        editorKit.styleSheet = s
        it.editorKit = editorKit
    }


    init {
        layout = BorderLayout()

        input.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                when (e.keyCode) {
                    KeyEvent.VK_ENTER -> {
                        resultView.text = DictionaryLookup.lookUp(input.text)
                        resultView.moveCaretPosition(0)
                    }
                    KeyEvent.VK_ESCAPE -> {
                        hideHandler()
                    }
//                    KeyEvent.VK_UP -> {
                    // TODO move resultView up/down
//                    }
//                    KeyEvent.VK_DOWN -> {
//                    }
                }
            }
        })

        input.also {
            it.border = LineBorder(JBUI.CurrentTheme.SearchEverywhere.searchFieldBorderColor())
            it.background = JBUI.CurrentTheme.SearchEverywhere.searchFieldBackground()
            it.font = it.font.deriveFont(18F)
        }

        resultView.also {
            it.isEditable = false
            it.border = EmptyBorder(0, 0, 0, 0)
            it.background = JBUI.CurrentTheme.SearchEverywhere.dialogBackground()
            it.font = UIUtil.getListFont().deriveFont(18F)
        }

        this.border = EmptyBorder(0, 0, 0, 0)
        this.background = JBUI.CurrentTheme.SearchEverywhere.dialogBackground()

        this.add(input, BorderLayout.NORTH)
        this.add(JBScrollPane(resultView), BorderLayout.CENTER)

        if (defaultText != null) {
            resultView.text = DictionaryLookup.lookUp(defaultText)
        }
    }

    companion object {
        fun createPopup(defaultText: String?): JBPopup {
            val component = DictionaryComponent(defaultText)
            val popup = JBPopupFactory.getInstance()
                    .createComponentPopupBuilder(component, component.input)
                    .setModalContext(false)
                    .setRequestFocus(true)
                    .setCancelOnClickOutside(true)
                    .setCancelKeyEnabled(false)
                    .setResizable(true)
                    .setMinSize(Dimension(320, 180))  // TODO
                    .setMovable(true)
                    .createPopup()
            component.hideHandler = {
                popup.cancel()
            }
            return popup
        }
    }
}
