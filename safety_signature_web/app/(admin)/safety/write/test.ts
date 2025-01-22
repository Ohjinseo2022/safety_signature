// 'use client'

// import styled from 'styled-components'
// import { useRef, useState } from 'react'
// import '@toast-ui/editor/dist/toastui-editor.css'
// import { Editor } from '@toast-ui/react-editor'
// import { SketchPicker } from 'react-color'

// interface WritePageProps {}
// const WritePage = ({}: WritePageProps) => {
//   const editorRef = useRef<Editor>(null)
//   const [color, setColor] = useState('#000000') // 기본 색상
//   const [isPickerVisible, setPickerVisible] = useState(false)

//   const [title, setTitle] = useState('')
//   const [files, setFiles] = useState<FileList | null>(null)

//   const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
//     setFiles(e.target.files)
//   }

//   const handleSubmit = (e: React.FormEvent) => {
//     e.preventDefault()
//     const content = editorRef.current?.getInstance().getMarkdown() || ''
//     console.log('제목:', title)
//     console.log('내용:', content)
//     console.log('첨부 파일:', files)
//     alert('게시글이 등록되었습니다!')
//   }

//   const handleColorChange = (newColor: any) => {
//     setColor(newColor.hex)
//   }

//   const applyTextColor = () => {
//     const editorInstance = editorRef.current?.getInstance()
//     console.log(editorRef.current)
//     // if (editorInstance) {
//     //   // 현재 에디터 모드 확인
//     //   const currentMode = editorInstance.getCurrentMode()

//     //   if (currentMode !== 'wysiwyg') {
//     //     editorInstance.changeMode('wysiwyg') // WYSIWYG 모드로 전환
//     //   }

//     //   const selectedText = editorInstance.getSelectedText()
//     //   if (selectedText) {
//     //     const coloredText = `<span style="color: ${color};">${selectedText}</span>`
//     //     editorInstance.insertHTML(coloredText)
//     //     setPickerVisible(false) // 색상 적용 후 팔레트 숨기기
//     //   } else {
//     //     alert('텍스트를 선택해주세요.')
//     //   }
//     // }
//   }

//   return (
//     <WriteContainer>
//       <h2>글 작성하기</h2>
//       <form onSubmit={handleSubmit}>
//         <FormGroup>
//           <label htmlFor="title">제목</label>
//           <input
//             id="title"
//             type="text"
//             placeholder="제목을 입력하세요"
//             value={title}
//             onChange={(e) => setTitle(e.target.value)}
//           />
//         </FormGroup>

//         <EditorContainer>
//           <label>내용</label>
//           <Toolbar $color={color}>
//             <div
//               className="color-button"
//               onClick={() => setPickerVisible(!isPickerVisible)}
//               title="텍스트 색상 변경"
//             />
//             {isPickerVisible && (
//               <div className="color-picker">
//                 <SketchPicker
//                   color={color}
//                   onChange={handleColorChange}
//                   onChangeComplete={applyTextColor}
//                 />
//               </div>
//             )}
//           </Toolbar>
//           <Editor
//             ref={editorRef}
//             height="500px"
//             initialEditType="wysiwyg"
//             language="ko-KR"
//             previewStyle="vertical"
//             useCommandShortcut={true}
//             hideModeSwitch={true} // 추가
//             placeholder="내용을 입력해주세요."
//           />
//         </EditorContainer>

//         <FileUploadContainer>
//           <label htmlFor="files">파일 첨부</label>
//           <input id="files" type="file" multiple onChange={handleFileChange} />
//         </FileUploadContainer>

//         <SubmitButton type="submit">등록하기</SubmitButton>
//       </form>
//     </WriteContainer>
//   )
// }

// export default WritePage

// const WriteContainer = styled.div`
//   background-color: #121212;
//   color: #e0e0e0;
//   padding: 20px;
//   border-radius: 8px;
//   box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
//   width: 100%;
//   max-width: 800px;
//   min-width: 600px;
//   min-height: calc(100vh - 80px); /* 최소 높이 설정 */
//   margin: 40px auto;
//   display: flex;
//   flex-direction: column;
//   gap: 20px;
// `

// const FormGroup = styled.div`
//   display: flex;
//   flex-direction: column;
//   gap: 8px;

//   label {
//     font-size: 16px;
//     color: #b0b0b0;
//   }

//   input {
//     padding: 12px;
//     border-radius: 4px;
//     border: 1px solid #444444;
//     background-color: #1e1e1e;
//     color: #e0e0e0;
//     font-size: 14px;

//     &::placeholder {
//       color: #666666;
//     }
//   }
// `

// const EditorContainer = styled.div`
//   .toastui-editor-defaultUI {
//     background-color: #1e1e1e;
//     color: #e0e0e0;
//     border-radius: 4px;
//     border: 1px solid #444444;
//   }
// `

// const Toolbar = styled.div<{ $color: string }>`
//   display: flex;
//   align-items: center;
//   gap: 8px;
//   margin-bottom: 20px;

//   .color-button {
//     width: 40px;
//     height: 40px;
//     border-radius: 50%;
//     background-color: ${({ $color }: { $color: string }) => $color};
//     border: 1px solid #ccc;
//     cursor: pointer;
//   }

//   .color-picker {
//     position: absolute;
//     z-index: 100;
//     margin-top: 10px;
//   }
// `

// const FileUploadContainer = styled.div`
//   display: flex;
//   flex-direction: column;
//   gap: 8px;
//   background-color: #1e1e1e;
//   padding: 10px;
//   border-radius: 4px;

//   label {
//     font-size: 16px;
//     color: #b0b0b0;
//   }

//   input[type='file'] {
//     color: #e0e0e0;
//   }
// `

// const SubmitButton = styled.button`
//   background-color: #60a5fa;
//   color: #ffffff;
//   border: none;
//   padding: 12px 16px;
//   border-radius: 4px;
//   font-size: 16px;
//   cursor: pointer;
//   transition: background-color 0.3s ease;

//   &:hover {
//     background-color: #3b82f6;
//   }
// `
// /
