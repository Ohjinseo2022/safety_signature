'use client'

import { useAlertStore } from '@/store/alertStore'
import { isEncryptedFile } from '@/utils/fileUtils'
import styled from 'styled-components'
import { useState } from 'react'
import { useRouter } from 'next/navigation'
import useFetchApi from '@/hooks/useFetchApi'
import { useInput } from '@/hooks/useInput'
import CommonEditor from '@/components/common/CommonEditor'
import CommonInput from '@/components/common/CommonInput'

const WritePage = () => {
  const { isModalVisible, onChangeModalVisible, callBackFunction } =
    useAlertStore()
  const [boardTitle, onChangeBoardTitle, setBoardTitle] = useInput<string>('')
  const [boardContents, onChangeBoardContents, setBoardContents] =
    useInput<string>('')
  const [files, setFiles] = useState<FileObj[] | null>(null)
  const router = useRouter()
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    //파일 이존재하면서
    if (e.target.files && e.target.files.length > 0) {
      const arrfileObj: FileObj[] = []
      Array.prototype.forEach.call(e.target.files, async (file) => {
        //암호화 된 파일인지 확인
        const isEncrypted = await isEncryptedFile(file)
        if (isEncrypted) {
          onChangeModalVisible({
            isVisible: true,
            msg: '암호화된 파일은 업로드 불가능합니다.',
          })
          return
        }
        arrfileObj.push({
          file: file,
          fileName: file.name,
          fileSize: file.size,
        })
      })
      setFiles(arrfileObj)
    } else {
      setFiles(null)
    }
    // setFiles((state) => (e.target?.files ? state.push(e.target.files) : state))
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    console.log('제목:', boardTitle)
    console.log('내용:', boardContents)
    console.log('첨부 파일:', files)
    if (!boardTitle) {
      onChangeModalVisible({ isVisible: true, msg: '제목을 입력해주세요.' })
      return
    }
    if (!boardContents) {
      onChangeModalVisible({ isVisible: true, msg: '내용을 입력해주세요.' })
      return
    }
    if (!files) {
      onChangeModalVisible({ isVisible: true, msg: '첨부파일은 필수입니다.' })
      return
    }
    const formData = new FormData()
    // ✅ 게시글 데이터 추가 (JSON으로 변환 후 Blob으로 추가)
    const boardData = JSON.stringify({
      boardTitle: boardTitle,
      boardContents: boardContents,
    })
    formData.append(
      'boardData',
      new Blob([boardData], { type: 'application/json' })
    )

    // ✅ 파일 데이터 추가
    files.forEach((fileObj, index) => {
      if (fileObj.file) {
        formData.append('files', fileObj.file) // files[] 배열로 추가
      }
    })

    const { data, error, status } = await useFetchApi(
      '/bulletin-board/registration',
      {
        method: 'post',
        data: formData,
        headers: 'multipart/form-data',
      },
      { isAuth: true }
    )
    if (status === 200) {
      onChangeModalVisible({
        isVisible: true,
        msg: '등록 완료 됐습니다.',
        callBackFunction: () => {
          router.push('/bulletin')
          return true
        },
      })
    }
  }
  return (
    <WriteContainer>
      <form onSubmit={handleSubmit}>
        <FormGroup>
          <CommonInput
            htmlFor="title"
            label="제목"
            placeholder="제목을 입력해주세요."
            type="text"
            value={boardTitle}
            onChange={onChangeBoardTitle}
          />
        </FormGroup>
        <br />
        <br />
        <FormGroup>
          <CommonEditor
            defaultValue={boardContents}
            onChange={onChangeBoardContents}
            placeholder="내용을 입력해주세요."
          ></CommonEditor>
        </FormGroup>

        <FileUploadContainer>
          <label htmlFor="files">파일 첨부</label>
          <input
            id="files"
            type="file"
            multiple={true}
            accept="image/jpeg, image/png, image/gif, application/pdf, application/msword, application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            onChange={handleFileChange}
          />
        </FileUploadContainer>
        <FormGroup>
          <SubmitButton type="submit">등록하기</SubmitButton>
        </FormGroup>
      </form>
    </WriteContainer>
  )
}

export default WritePage
const WriteContainer = styled.div`
  background-color: #121212;
  color: #e0e0e0;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  width: 100%;
  max-width: 800px;
  min-width: 600px;
  min-height: calc(100vh - 80px);
  margin: 40px auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
`

const FormGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  label {
    font-size: 18px;
    color: #b0b0b0;
  }

  input,
  textarea {
    padding: 12px;
    border-radius: 4px;
    border: 1px solid #444444;
    background-color: #1e1e1e;
    color: #e0e0e0;
    font-size: 14px;

    &::placeholder {
      color: #666666;
    }
  }

  textarea {
    min-height: 200px; /* 기본 높이 */
    resize: vertical; /* 크기 조절 가능 */
  }
  button {
  }
`

const FileUploadContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  background-color: #1e1e1e;
  padding: 10px;
  border-radius: 4px;

  label {
    font-size: 16px;
    color: #b0b0b0;
  }

  input[type='file'] {
    color: #e0e0e0;
  }
`

const SubmitButton = styled.button`
  width: 150px;
  background-color: #60a5fa;
  color: #ffffff;
  border: none;
  padding: 12px 16px;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: #3b82f6;
  }
`
