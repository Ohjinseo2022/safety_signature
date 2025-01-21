'use client'

import styled from 'styled-components'
import { useEffect, useState } from 'react'
import Link from 'next/link'
import CommonBoard, { PostsType } from '@/components/common/Board'

interface SafetyPageProps {}

const SafetyPage = ({}: SafetyPageProps) => {
  const posts: PostsType[] = [
    {
      id: '0JGYVMZFRMVWG',
      title: '게시글 제목 1',
      site: '현장명 1',
      author: '작성자 1',
      createdDate: '2025-01-20',
      path: '/safety/detail/',
      signature: 5,
    },
    {
      id: '게시글 제목2',
      title: '게시글 제목 2',
      site: '현장명 2',
      author: '작성자 2',
      createdDate: '2025-01-19',
      path: '/safety/detail/',
      signature: 2,
    },
    {
      id: '게시글 제목3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/safety/detail/',
      signature: 0,
    },
    {
      id: '게시글 제321목3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/safety/detail/',
      signature: 0,
    },
    {
      id: '게시글 제ㄹㅁㄴ목3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/safety/detail/',
      signature: 0,
    },
    {
      id: '게시글 제목ㄷ13',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/safety/detail/',
      signature: 0,
    },
    {
      id: '게시글 제목21ㅎ윤3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/safety/detail/',
      signature: 0,
    },
    {
      id: '게시글 제ㅊ튜목3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/safety/detail/',
      signature: 0,
    },
    {
      id: '게시글 ㄷㅈㅂ제목3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/safety/detail/',
      signature: 0,
    },
    {
      id: '게시글 ㄷㅈㅂ제11ㅎㅎ목3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/safety/detail/',
      signature: 0,
    },
  ]
  const headers = [
    { label: '글제목', columns: 'title' },
    { label: '현장명', columns: 'site' },
    { label: '작성자', columns: 'author' },
    { label: '작성일자', columns: 'createdDate' },
    { label: '결제완료', columns: 'signature' },
  ]
  const handleSearch = (e: any) => {
    e.preventDefault()
    alert('검색 실행')
  }

  const SearchContainer = styled.div`
    background-color: #1e1e1e;
    padding: 20px;
    border-radius: 8px;
    margin-bottom: 20px;

    form {
      display: flex;
      flex-direction: column;
      gap: 16px;
    }

    .search-fields {
      display: flex;
      flex-wrap: wrap;
      gap: 16px;
      justify-content: space-between;

      .search-field {
        display: flex;
        flex-direction: column;
        flex: 1;
        min-width: 200px;

        label {
          margin-bottom: 8px;
          color: #b0b0b0;
          font-size: 14px;
        }

        input,
        select {
          padding: 10px;
          border-radius: 4px;
          border: 1px solid #444444;
          background-color: #2a2a2a;
          color: #e0e0e0;
          font-size: 14px;
          height: 40px;
        }

        input::placeholder {
          color: #666666;
        }
      }

      .search-button {
        display: flex;
        align-items: end;
        justify-content: flex-end;

        button {
          background-color: #60a5fa;
          color: #ffffff;
          border: none;
          padding: 10px 16px;
          border-radius: 4px;
          cursor: pointer;
          transition: background-color 0.3s ease;
          height: 40px;

          &:hover {
            background-color: #3b82f6;
          }
        }
      }
    }
  `

  return (
    <CommonBoard
      title={'안전문서 게시판'}
      headers={headers}
      createBtn={() => {}}
      posts={posts}
      children={
        <SearchContainer>
          <form onSubmit={handleSearch}>
            <div className="search-fields">
              <div className="search-field">
                <label htmlFor="title">검색</label>
                <input
                  id="title"
                  type="text"
                  placeholder="검색 내용을 입력하세요"
                />
              </div>
              <div className="search-button">
                <button type="submit">검색</button>
              </div>
            </div>
          </form>

          <form onSubmit={handleSearch}>
            <div className="search-fields">
              <div className="search-field">
                <label htmlFor="title">제목 입력</label>
                <input id="title" type="text" placeholder="제목을 입력하세요" />
              </div>

              <div className="search-field">
                <label htmlFor="newStatus">신규 작성글 여부</label>
                <select id="newStatus">
                  <option value="">전체</option>
                  <option value="yes">신규</option>
                  <option value="no">기존</option>
                </select>
              </div>

              <div className="search-field">
                <label htmlFor="author">작성자 입력</label>
                <input
                  id="author"
                  type="text"
                  placeholder="작성자를 입력하세요"
                />
              </div>

              <div className="search-field">
                <label htmlFor="site">현장 정보 입력</label>
                <input
                  id="site"
                  type="text"
                  placeholder="현장 정보를 입력하세요"
                />
              </div>

              <div className="search-button">
                <button type="submit">검색</button>
              </div>
            </div>
          </form>
        </SearchContainer>
      }
    ></CommonBoard>
  )
}

export default SafetyPage
