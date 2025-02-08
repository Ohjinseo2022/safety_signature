'use client'

import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import { useInput } from '@/hooks/useInput'
import CommonBoard, { PostsType } from '@/components/common/CommonBoard'
import CommonSearchBar from '@/components/common/CommonSearchBar'
import { useBulletinBoardListQuery } from './_hooks/BulletinBoardQuery'

interface BulletinPageProps {}

const BulletinPage = ({}: BulletinPageProps) => {
  const router = useRouter()
  const { data: bulletinBoardList = { data: [] }, refetch } =
    useBulletinBoardListQuery({
      boardTitle: '',
      createdBy: '',
      startDate: '2025-02-01',
      endDate: '2025-02-10',
      page: 0,
      size: 10,
      isOwner: false,
    })
  const posts: PostsType[] = [
    {
      id: '0JGYVMZFRMVWG',
      title: '게시글 제목 1',
      site: '현장명 1',
      author: '작성자 1',
      createdDate: '2025-01-22',
      path: '/bulletin/detail/',
      signature: 5,
    },
    {
      id: '게시글 제목2',
      title: '게시글 제목 2',
      site: '현장명 2',
      author: '작성자 2',
      createdDate: '2025-01-19',
      path: '/bulletin/detail/',
      signature: 2,
    },
    {
      id: '게시글 제목3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-22',
      path: '/bulletin/detail/',
      signature: 0,
    },
    {
      id: '게시글 제321목3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/bulletin/detail/',
      signature: 0,
    },
    {
      id: '게시글 제ㄹㅁㄴ목3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/bulletin/detail/',
      signature: 0,
    },
    {
      id: '게시글 제목ㄷ13',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/bulletin/detail/',
      signature: 0,
    },
    {
      id: '게시글 제목21ㅎ윤3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/bulletin/detail/',
      signature: 0,
    },
    {
      id: '게시글 제ㅊ튜목3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/bulletin/detail/',
      signature: 0,
    },
    {
      id: '게시글 ㄷㅈㅂ제목3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/bulletin/detail/',
      signature: 0,
    },
    {
      id: '게시글 ㄷㅈㅂ제11ㅎㅎ목3',
      title: '게시글 제목 3',
      site: '현장명 3',
      author: '작성자 3',
      createdDate: '2025-01-18',
      path: '/bulletin/detail/',
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
    console.log(e)
    alert('검색 실행')
  }
  const [optionValue, onChangeOptionValue] = useInput('')

  return (
    <CommonBoard
      title={'안전문서 게시판'}
      headers={headers}
      createBtn={() => {
        router.push('/bulletin/write')
      }}
      posts={posts}
      children={
        <CommonSearchBar
          onSubmit={handleSearch}
          label="안전문서 제목"
          placeholder="검색 내용을 입력하세요"
        />
      }
    ></CommonBoard>
  )
}
export default BulletinPage
