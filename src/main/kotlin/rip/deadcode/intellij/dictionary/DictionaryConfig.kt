package rip.deadcode.intellij.dictionary

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.options.Configurable
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import java.awt.FlowLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

class DictionaryConfig : Configurable, Configurable.NoScroll {

    private val tbDefaultPrefix = JBTextField()

    private val component = JPanel(FlowLayout(FlowLayout.LEFT)).also {
        it.border = EmptyBorder(2, 2, 2, 2)
        it.add(JPanel(GridBagLayout()).apply {
            add(JBLabel("Dictionary plugin settings"), GridBagConstraints().apply {
                anchor = GridBagConstraints.WEST
                gridx = 0
                gridy = 0
            })
            add(JBLabel("Default prefix"), GridBagConstraints().apply {
                anchor = GridBagConstraints.WEST
                gridx = 0
                gridy = 1
                insets = Insets(0, 0, 0, 4)
            })
            add(tbDefaultPrefix, GridBagConstraints().apply {
                anchor = GridBagConstraints.WEST
                gridx = 1
                gridy = 1
            })
        })
    }

    override fun isModified(): Boolean {
        return tbDefaultPrefix.text != getDefaultPrefix()
    }

    override fun getDisplayName(): String = "Dictionary Plugin"

    override fun apply() {
        setDefaultPrefix(tbDefaultPrefix.text)
    }

    override fun createComponent(): JComponent? {
        tbDefaultPrefix.text = getDefaultPrefix()
        return component
    }

    companion object {

        private const val keyDefaultPrefix = "rip.deadcode.intellij.dictionary.defaultPrefix"

        fun setDefaultPrefix(prefix: String) {
            PropertiesComponent.getInstance().setValue(keyDefaultPrefix, prefix)
        }

        fun getDefaultPrefix(): String? = PropertiesComponent.getInstance().getValue(keyDefaultPrefix)
    }
}
