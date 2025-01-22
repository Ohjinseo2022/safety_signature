'use client'

import { durationDay, nowDate } from '@/utils/utils'
import styled from 'styled-components'

export interface PostsType {
  id: string
  title: string
  site: string
  author: string
  createdDate: string
  signature: string | number
  path?: string
  [key: string]: any
}

interface BoardProps {
  title: string
  headers: { label: string; columns: string }[]
  posts: PostsType[]
  createBtn: () => void
  children?: React.ReactNode
}

const CommonBoard = ({
  title,
  headers,
  posts,
  createBtn,
  children,
}: BoardProps) => {
  return (
    <BoardContainer>
      <BoardHeader>
        <h1>{title}</h1>
      </BoardHeader>
      {children ?? null}
      <br />
      <BoardTableHeader>
        {headers.map((header, idx) => (
          <span key={`${header.columns}-${idx}`}>{header.label}</span>
        ))}
      </BoardTableHeader>
      <BoardList $isNew={true}>
        {posts.map((post) => (
          <li key={post.id}>
            {headers.map((header, idx) => {
              if (header.columns === 'title' && post.path) {
                return (
                  <CustomLink
                    $isNew={durationDay(post.createdDate, nowDate()) <= 1}
                    key={`${header.columns}-${post.id}`}
                    href={`${post.path}${post.id}`}
                  >
                    {post[header.columns]}
                  </CustomLink>
                )
              } else {
                return (
                  <span key={`${header.columns}-${post.id}`}>
                    {post[header.columns]}
                  </span>
                )
              }
            })}
          </li>
        ))}
      </BoardList>
      <StickyContainer>
        <FloatingButton
          onClick={() => {
            createBtn()
          }}
        >
          글쓰기
        </FloatingButton>
      </StickyContainer>
    </BoardContainer>
  )
}

export default CommonBoard

// 스타일 정의
const BoardContainer = styled.div`
  background-color: #121212;
  color: #e0e0e0;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  width: 100%;
  max-width: 1200px;
  min-width: 800px;
  margin: 40px auto;
  min-height: calc(100vh - 80px);
  display: flex;
  flex-direction: column;
  position: relative;
`

const BoardHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 10px;
  border-bottom: 1px solid #333333;
  margin-bottom: 20px;

  h1 {
    font-size: 24px;
    margin: 0;
  }
`

const BoardTableHeader = styled.div`
  display: grid;
  grid-template-columns: 1fr 100px 120px 120px 80px; /* BoardList와 동일한 그리드 구성 */
  padding: 12px 16px;
  background-color: #1e1e1e;
  border-radius: 4px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #ffffff;
  text-align: center;

  span:first-child {
    text-align: left; /* 첫 번째 열은 왼쪽 정렬 */
  }
`

const StickyContainer = styled.div`
  position: sticky; /* sticky로 설정 */
  bottom: 20px; /* 컨테이너의 하단 기준으로 20px 위로 */
`

const FloatingButton = styled.button`
  /* position: sticky; */
  width: 150px;
  right: 20px; /* 오른쪽으로 고정 */
  bottom: 20px; /* 컨테이너 하단에서 20px 위로 */
  background-color: #60a5fa;
  color: #ffffff;
  border: none;
  padding: 12px 24px;
  border-radius: 8px;
  cursor: pointer;
  transition:
    background-color 0.3s ease,
    transform 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);

  &:hover {
    background-color: #3b82f6;
  }
`
interface BoardListCssType {
  $isNew?: boolean
}

const BoardList = styled.ul<BoardListCssType>`
  list-style: none;
  margin: 0;
  padding: 0;
  flex: 1;

  li {
    display: grid;
    grid-template-columns: 1fr 100px 120px 120px 80px; /* BoardTableHeader와 동일한 그리드 구성 */
    align-items: center;
    padding: 12px 16px;
    background-color: #1e1e1e;
    margin-bottom: 10px;
    border-radius: 4px;
    transition: background-color 0.3s ease;

    &:hover {
      background-color: #333333;
    }

    span {
      color: #b0b0b0;
      font-size: 14px;
      text-align: center;
    }

    span:first-child {
      text-align: left; /* 첫 번째 열은 왼쪽 정렬 */
    }
  }
`
const CustomLink = styled.a<BoardListCssType>`
  text-decoration: none;
  color: #60a5fa !important;
  position: relative;

  &:hover {
    color: #3b82f6 !important;
  }

  &::after {
    content: '${({ $isNew }) => ($isNew ? 'N' : '')}';
    display: ${({ $isNew }) => ($isNew ? 'inline-block' : 'none')} !important;
    background-color: #ff4d4f !important;
    color: #ffffff !important;
    font-size: 10px; /* 아이콘 크기 축소 */
    font-weight: bold;
    padding: 2px 4px; /* 패딩 축소 */
    border-radius: 4px !important;
    margin-left: 6px;
    position: absolute;
    transform: translateY(-50%);
  }
`
