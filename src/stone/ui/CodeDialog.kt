package stone.ui

import java.io.*
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.JScrollPane
import javax.swing.JTextArea

/**
 * Created by Junya on 2016/03/18.
 */

class CodeDialog : Reader() {
    private var buffer: String? = null
    private var pos: Int = 0

    override fun close() {
    }

    override fun read(cbuf: CharArray?, off: Int, len: Int): Int {
        if (buffer == null) {
            val input = showDialog()
            if (input == null) {
                return -1
            } else {
                print(input)
                buffer = input + "\n"
                pos = 0
            }
        }

        var size = 0
        val length = buffer!!.length
        while (pos < length && size < len) {
            cbuf!![off + size++] = buffer!![pos++]
        }
        if (pos == length) {
            buffer = null
        }
        return size
    }


    protected fun showDialog() : String? {
        val area = JTextArea(20, 40)
        val pane = JScrollPane(area)
        val result = JOptionPane.showOptionDialog(null, pane, "Input",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, null, null)

        return if (result == JOptionPane.OK_OPTION) {
            area.getText()
        } else {
            null
        }
    }

    companion object {
        fun file() : Reader {
            val chooser = JFileChooser()
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                return BufferedReader(FileReader(chooser.selectedFile))
            } else {
                throw FileNotFoundException("no file specified")
            }
        }
    }
}

