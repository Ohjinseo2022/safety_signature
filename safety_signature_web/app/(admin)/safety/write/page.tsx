'use client'

import styled from 'styled-components'
import { useState } from 'react'
import { useInput } from '@/hooks/useInput'
import CommonEditor from '@/components/common/CommonEditor'
import CommonInput from '@/components/common/CommonInput'
import CommonModal from '@/components/modal/CommonModal'

const WritePage = () => {
  const [title, onChangeTitle, setTitle] = useInput('')
  const [content, onChangeContent, setContent] = useInput('')
  const [files, setFiles] = useState<FileList | null>(null)

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFiles(e.target.files)
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    console.log('제목:', title)
    console.log('내용:', content)
    console.log('첨부 파일:', files)
    console.log(files)
    if (!files) {
      alert('파일이 없어연')
      return
    }
    alert('게시글이 등록되었습니다!')
  }
  const [isModalVisible, onChangeModelVisible, setModalVisible] =
    useInput(false)

  const openModal = () => setModalVisible(true)
  const closeModal = () => setModalVisible(false)

  return (
    <WriteContainer>
      <div>
        <button onClick={openModal}>모달 열기</button>
        <CommonModal
          isVisible={isModalVisible}
          title="공통 모달"
          setIsVisible={onChangeModelVisible}
        >
          <p>이 모달은 재사용 가능한 공통 모달 컴포넌트입니다.</p>
          <p>다른 내용도 여기에 추가할 수 있습니다.</p>
        </CommonModal>
      </div>
      <form onSubmit={handleSubmit}>
        <FormGroup>
          <CommonInput
            htmlFor="title"
            label="제목"
            placeholder="제목을 입력해주세요."
            type="text"
            value={title}
            onChange={onChangeTitle}
          />
        </FormGroup>
        <br />
        <br />
        <FormGroup>
          <CommonEditor
            defaultValue={content}
            onChange={onChangeContent}
            placeholder="내용을 입력해주세요."
          ></CommonEditor>
        </FormGroup>

        <FileUploadContainer>
          <label htmlFor="files">파일 첨부</label>
          <input id="files" type="file" multiple onChange={handleFileChange} />
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
