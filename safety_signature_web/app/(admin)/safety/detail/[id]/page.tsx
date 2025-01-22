'use client'

import styled from 'styled-components'
import { use, useEffect, useState } from 'react'

// 스타일드 컴포넌트 정의
const DetailContainer = styled.div`
  background-color: #121212;
  color: #e0e0e0;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  width: 100%;
  max-width: 1200px;
  min-width: 800px;
  min-height: calc(100vh - 80px); /* 최소 높이 설정 */
  margin: 40px auto;
  display: flex;
  flex-direction: column;
  gap: 20px;
`

const PostInfo = styled.div`
  background-color: #1e1e1e;
  padding: 40px; /* 여유 공간 추가 */
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 20px;

  h2 {
    margin: 0;
    font-size: 24px;
  }

  .content {
    margin: 0;
    font-size: 16px;
    color: #b0b0b0;
    background-color: #2a2a2a; /* 배경 추가 */
    padding: 20px; /* 내부 여백 */
    border-radius: 8px;
    min-height: 200px; /* 최소 높이 설정 */
  }
`

const DownloadSection = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #1e1e1e;
  border-radius: 8px;

  button {
    background-color: #60a5fa;
    color: #ffffff;
    border: none;
    padding: 10px 16px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;

    &:hover {
      background-color: #3b82f6;
    }
  }
`

const CommentsContainer = styled.div`
  background-color: #1e1e1e;
  padding: 20px;
  border-radius: 8px;
`

const CommentsHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;

  h3 {
    margin: 0;
    font-size: 20px;
  }

  button {
    background-color: #60a5fa;
    color: #ffffff;
    border: none;
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;

    &:hover {
      background-color: #3b82f6;
    }
  }
`

const CommentsTable = styled.table`
  width: 100%;
  border-collapse: collapse;
  text-align: left;
  font-size: 14px;

  thead {
    background-color: #333333;

    th {
      padding: 10px;
      border: 1px solid #444444;
      color: #ffffff;
    }
  }

  tbody {
    tr {
      &:hover {
        background-color: #2a2a2a;
      }

      td {
        padding: 10px;
        border: 1px solid #444444;
        color: #b0b0b0;
      }
    }
  }
`
interface SafetyDetailPageProps {
  params: Promise<{ id: string }>
}

const SafetyDetailPage = ({ params }: SafetyDetailPageProps) => {
  const unwrappedParams = use(params) // params를 언래핑
  const post = {
    title: '게시글 제목',
    content: '게시글 내용입니다. 관리자가 작성한 내용을 포함합니다.',
    documents: [
      { id: 1, name: '문서1.pdf', url: '/downloads/doc1.pdf' },
      { id: 2, name: '문서2.pdf', url: '/downloads/doc2.pdf' },
    ],
  }

  const comments = [
    {
      id: 1,
      docName: '문서1.pdf',
      name: '홍길동',
      signature: '✔️',
      time: '2025-01-22 10:00',
      site: '현장1',
    },
    {
      id: 2,
      docName: '문서2.pdf',
      name: '김철수',
      signature: '✔️',
      time: '2025-01-20 11:00',
      site: '현장2',
    },
  ]

  const downloadExcel = () => {
    alert('댓글 데이터를 엑셀로 다운로드합니다.')
  }

  return (
    <DetailContainer>
      <PostInfo>
        <h2>{post.title}</h2>
        <div className="content">{post.content}</div>
      </PostInfo>

      <DownloadSection>
        <h3>첨부된 문서</h3>
        <div>
          {post.documents.map((doc) => (
            <a
              key={doc.id}
              href={doc.url}
              download
              style={{ marginRight: '10px', color: '#60a5fa' }}
            >
              {doc.name}
            </a>
          ))}
        </div>
      </DownloadSection>

      <CommentsContainer>
        <CommentsHeader>
          <h3>댓글 목록</h3>
          <button onClick={downloadExcel}>엑셀 다운로드</button>
        </CommentsHeader>

        <CommentsTable>
          <thead>
            <tr>
              <th>문서명</th>
              <th>이름</th>
              <th>서명</th>
              <th>시간</th>
              <th>현장명</th>
            </tr>
          </thead>
          <tbody>
            {comments.map((comment) => (
              <tr key={comment.id}>
                <td>{comment.docName}</td>
                <td>{comment.name}</td>
                <td>{comment.signature}</td>
                <td>{comment.time}</td>
                <td>{comment.site}</td>
              </tr>
            ))}
          </tbody>
        </CommentsTable>
      </CommentsContainer>
    </DetailContainer>
  )
}

export default SafetyDetailPage
