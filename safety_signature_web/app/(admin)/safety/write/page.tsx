'use client'

import styled from 'styled-components'
import { useState } from 'react'
import CommonEditor from '@/components/common/CommonEditor'

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
    font-size: 16px;
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

const WritePage = () => {
  const [title, setTitle] = useState('')
  const [content, setContent] = useState('')
  const [files, setFiles] = useState<FileList | null>(null)

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFiles(e.target.files)
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    console.log('제목:', title)
    console.log('내용:', content)
    console.log('첨부 파일:', files)
    alert('게시글이 등록되었습니다!')
  }

  return (
    <WriteContainer>
      <h2>글 작성하기</h2>
      <form onSubmit={handleSubmit}>
        <FormGroup>
          <label htmlFor="title">제목</label>
          <input
            id="title"
            type="text"
            placeholder="제목을 입력하세요"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </FormGroup>

        <FormGroup>
          <label htmlFor="content">내용</label>
          <CommonEditor></CommonEditor>
          <textarea
            id="content"
            placeholder="내용을 입력하세요"
            value={content}
            onChange={(e) => setContent(e.target.value)}
          />
        </FormGroup>

        <FileUploadContainer>
          <label htmlFor="files">파일 첨부</label>
          <input id="files" type="file" multiple onChange={handleFileChange} />
        </FileUploadContainer>

        <SubmitButton type="submit">등록하기</SubmitButton>
      </form>
    </WriteContainer>
  )
}

export default WritePage
