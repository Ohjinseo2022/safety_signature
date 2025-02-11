'use client'

import { customDatePlus, dateFormat, nowDate } from '@/utils/utils'
import { useEffect, useMemo, useState } from 'react'
import { useRouter } from 'next/navigation'
import { useInput } from '@/hooks/useInput'
import CommonBoard, { PostsType } from '@/components/common/CommonBoard'
import CommonSearchBar from '@/components/common/CommonSearchBar'
import BulletinBoardMultiSearchBar from './_components/BulletinBoardMultiSearchBar'
import { useBulletinBoardListQuery } from './_hooks/BulletinBoardQuery'

interface BulletinPageProps {}

const BulletinPage = ({}: BulletinPageProps) => {
  const router = useRouter()
  const [searchInput, onChangeSearchInput] = useInput<string>('')
  const [startDate, onChangeStartDate, setStartDate] = useInput<string>(
    customDatePlus(nowDate('YYYY-MM-DD'), '-30')
  )
  const [endDate, onChangeEndDate, setEndDate] = useInput<string>(
    nowDate('YYYY-MM-DD')
  )
  const [isCreated, onChangeIsCreated, setIsCreated] = useInput<boolean>(false)
  const [createdBy, onChangeCreatedBy, setCreatedBy] = useInput<string>('')
  const [page, onChangePage, setPage] = useInput<number>(0)

  const { data: bulletinBoardList = { data: [], count: 0 }, refetch } =
    useBulletinBoardListQuery({
      boardTitle: searchInput,
      createdBy: '',
      startDate: startDate,
      endDate: endDate,
      page: page,
      size: 10,
      isOwner: isCreated,
    })
  const boardList = useMemo(() => {
    const result = bulletinBoardList.data.map(
      (e: BulletinBoardMasterType, idx: number) => {
        return {
          id: e.id,
          path: '/bulletin/detail/',
          boardTitle: e.boardTitle,
          createdBy: e.userMasterDTO.name,
          createdDate: e.createdDateFormat,
          site: '추가예정',
          signature: '추가예정',
        }
      }
    )
    console.log(result)
    return result
  }, [bulletinBoardList])
  const headers = [
    { label: '글제목', columns: 'boardTitle' },
    { label: '현장명', columns: 'site' },
    { label: '작성자', columns: 'createdBy' },
    { label: '작성일자', columns: 'createdDate' },
    { label: '결제완료', columns: 'signature' },
  ]
  const handleSearch = async (e: any) => {
    // console.log(e)
    // await refetch()
    // alert('검색 실행')
  }

  return (
    <CommonBoard
      title={'안전문서 게시판'}
      headers={headers}
      createBtn={() => {
        router.push('/bulletin/write')
      }}
      posts={boardList}
      children={
        <BulletinBoardMultiSearchBar
          onSubmit={handleSearch}
          label="안전문서 제목"
          placeholder="검색 내용을 입력하세요"
          startDate={startDate}
          endDate={endDate}
          searchInput={searchInput}
          onChangeStartDate={onChangeStartDate}
          onChangeEndDate={onChangeEndDate}
          onChangeInput={onChangeSearchInput}
          checked={isCreated}
          onChangeChecked={(e) => setIsCreated(e.target.checked)}
        />
      }
    ></CommonBoard>
  )
}
export default BulletinPage
