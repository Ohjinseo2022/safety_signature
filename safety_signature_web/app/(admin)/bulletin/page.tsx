'use client'

import {
  customDatePlus,
  dateFormat,
  datePlus,
  nowDate,
  pagerSet,
} from '@/utils/utils'
import styled from 'styled-components'
import { useEffect, useMemo, useState } from 'react'
import { useRouter } from 'next/navigation'
import { useInput } from '@/hooks/useInput'
import CommonBoard, { PostsType } from '@/components/common/CommonBoard'
import CommonPagination from '@/components/common/CommonPagination'
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
  const [page, onChangePage, setPage] = useInput<number>(1)

  const { data: bulletinBoardList = { data: [], count: 0 }, refetch } =
    useBulletinBoardListQuery({
      boardTitle: searchInput,
      createdBy: createdBy,
      startDate: startDate,
      endDate: datePlus(endDate, '1'),
      page: page - 1,
      size: 10,
      isOwner: isCreated,
    })
  const paginationSet = useMemo(() => {
    return pagerSet(page, '10')
  }, [bulletinBoardList])
  const boardList = useMemo(() => {
    if (
      bulletinBoardList &&
      bulletinBoardList.data &&
      bulletinBoardList.data.length > 0
    ) {
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
    } else {
      return []
    }
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
    <BulleitnBoardContainar>
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
            startDate={startDate}
            endDate={endDate}
            searchInput={searchInput}
            createdBy={createdBy}
            onChangeStartDate={onChangeStartDate}
            onChangeEndDate={onChangeEndDate}
            onChangeInput={onChangeSearchInput}
            checked={isCreated}
            onChangeCreatedBy={onChangeCreatedBy}
            onChangeChecked={(e) => setIsCreated(e.target.checked)}
          />
        }
      ></CommonBoard>
      <CommonPagination
        currentPage={page}
        onPageChange={onChangePage}
        totalPages={paginationSet.length}
      />
    </BulleitnBoardContainar>
  )
}
export default BulletinPage
const BulleitnBoardContainar = styled.div`
  width: 100%;
`
