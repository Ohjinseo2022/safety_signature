'use client'

import styled from 'styled-components'
import { use, useEffect, useMemo, useState } from 'react'
import CommonDataTable from '@/components/common/CommonDataTable'
import CustomDownloadButton from '@/components/custom/CustomDownloadButton'
import { LoginResponseSuccess } from '@/app/(common)/user/login/_userRepository/types'
import { useUserProfile } from '@/app/(common)/user/login/_userState/userStore'
import { useBulletinBoardQuery } from '../../_hooks/BulletinBoardQuery'

interface BulletinDetailPageProps {
  params: Promise<{ id: string }>
}

const BulletinDetailPage = ({ params }: BulletinDetailPageProps) => {
  const unwrappedParams = use(params) // params를 언래핑
  const userProfile = useUserProfile().userProfile as LoginResponseSuccess
  const { data, error, isFetched } = useBulletinBoardQuery(unwrappedParams.id)
  const detailData: BulletinBoardMasterType = useMemo(() => {
    if (isFetched && data) {
      return data.data
    }
    return undefined
  }, [isFetched, data])
  console.log('unwrappedParams : ', unwrappedParams)
  const headers = [
    { label: '문서명', columns: 'docName' },
    { label: '이름', columns: 'name' },
    { label: '서명', columns: 'signature' },
    { label: '시간', columns: 'time' },
    { label: '현장명', columns: 'site' },
  ]
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
  const onSignatureHandler = () => {
    /**
     * 로그인한 유저의 권한 정보를 읽고 해당 계정의 사인정보기반으로 결제처리
     */
  }
  return detailData ? (
    <DetailContainer>
      <PostInfo>
        <h2>{detailData.boardTitle}</h2>
        <div
          className="content"
          dangerouslySetInnerHTML={{ __html: detailData.boardContents }}
        ></div>
      </PostInfo>

      <DownloadSection>
        <h3>첨부된 문서</h3>
        <div>
          {detailData.attachDocList && detailData.attachDocList.length > 0 ? (
            detailData.attachDocList.map((doc: AttachDocMasterType, idx) => (
              <CustomDownloadButton key={doc.id} file={doc} />
            ))
          ) : (
            <span>첨부된 문서가 없습니다.</span>
          )}
        </div>
      </DownloadSection>
      <CommonDataTable
        title={'결제완료 리스트'}
        topBtnLable={'결제하기'}
        onTopButtonClick={onSignatureHandler}
        headers={headers}
        dataItem={comments}
      />
    </DetailContainer>
  ) : (
    <></>
  )
}

export default BulletinDetailPage

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
