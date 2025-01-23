import SunEditor from 'suneditor-react'
import { useRef } from 'react'
import 'suneditor/dist/css/suneditor.min.css'
import SunEditorCore from 'suneditor/src/lib/core'

interface EditorProps {
  placeholder?: string
  defaultValue?: string
  onChange?: (content: string) => void
  height?: string
  disabled?: boolean
  hideToolbar?: boolean
}

const CommonEditor: React.FC<EditorProps> = ({
  placeholder,
  defaultValue,
  onChange,
  height = '550px',
  disabled = false,
  hideToolbar = false,
}) => {
  const editor = useRef<SunEditorCore>(null)

  const getSunEditorInstance = (sunEditor: SunEditorCore) => {
    editor.current = sunEditor
  }

  return (
    <>
      <SunEditor
        placeholder={placeholder}
        width="100%"
        height={height}
        setOptions={{
          buttonList: [
            [
              'fontSize',
              'bold',
              'underline',
              'italic',
              'strike',
              'fontColor',
              'hiliteColor',
              'image',
              'font',
              'table',
            ],
          ],
        }}
        setContents={defaultValue}
        getSunEditorInstance={getSunEditorInstance}
        readOnly={disabled}
        hideToolbar={hideToolbar}
        onChange={(e) => {
          onChange?.(e)
        }}
      />
    </>
  )
}

export default CommonEditor
