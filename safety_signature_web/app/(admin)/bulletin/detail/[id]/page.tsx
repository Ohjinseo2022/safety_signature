'use client'

import { useAlertStore } from '@/store/alertStore'
import styled from 'styled-components'
import { use, useEffect, useMemo, useState } from 'react'
import { getDownloadExcel } from '@/hooks/common/useDownLoad'
import { useInput } from '@/hooks/useInput'
import CommonDataTable from '@/components/common/CommonDataTable'
import CustomDownloadButton from '@/components/custom/CustomDownloadButton'
import { CommonCard } from '@/components/styled/common'
import {
  LoginResponseSuccess,
  UserTypeCode,
} from '@/app/(common)/user/login/_userRepository/types'
import { useUserProfile } from '@/app/(common)/user/login/_userState/userStore'
import {
  ApproveMasterType,
  approveSignature,
  useApproveListQuery,
  useBulletinBoardQuery,
} from '../../_hooks/BulletinBoardQuery'

interface BulletinDetailPageProps {
  params: Promise<{ id: string }>
}

const BulletinDetailPage = ({ params }: BulletinDetailPageProps) => {
  const unwrappedParams = use(params) // params를 언래핑
  const { isModalVisible, onChangeModalVisible, callBackFunction } =
    useAlertStore()
  const userProfile = useUserProfile().userProfile as LoginResponseSuccess
  const {
    data: bulletinBoardDetail,
    error,
    isFetched,
  } = useBulletinBoardQuery(unwrappedParams.id)
  const [page, onChangePage, setPage] = useInput<number>(1)
  // 결제 완료 리스트
  const {
    data: approveList,
    error: approveListError,
    isFetched: approveListIsFetched,
    refetch: approveListRefetch,
  } = useApproveListQuery({
    bulletinBoardId: unwrappedParams.id,
    page: page - 1,
    size: 10,
  })
  const detailData: BulletinBoardMasterType = useMemo(() => {
    if (isFetched && bulletinBoardDetail) {
      return bulletinBoardDetail.data
    }
    return undefined
  }, [isFetched, bulletinBoardDetail])

  const approveDataList: ApproveMasterType[] = useMemo(() => {
    if (approveListIsFetched && approveList) {
      return approveList.data
    }
    return []
  }, [approveList, approveListIsFetched])
  //console.log('unwrappedParams : ', unwrappedParams)
  const headers = [
    { label: '문서명', columns: 'bulletinBoardTitle' },
    { label: '이름', columns: 'userName' },
    { label: '서명', columns: 'attachDocId' },
    { label: '시간', columns: 'createdDateFormat' },
    { label: '현장명', columns: 'siteName' },
  ]

  const onDownLoadExcel = async () => {
    await getDownloadExcel(unwrappedParams.id)
    alert('댓글 데이터를 엑셀로 다운로드합니다.')
  }
  const onSignatureHandler = async () => {
    const result = await approveSignature(unwrappedParams.id)
    if (result) {
      onChangeModalVisible({ isVisible: true, msg: result.message })
      result.status === 200 ? approveListRefetch() : undefined
    }
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
      <CommonCard>
        <h3>현장 주소 정보</h3>
        <div> {detailData.siteAddress}</div>
      </CommonCard>
      <CommonCard>
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
      </CommonCard>

      <CommonDataTable
        title={'결제완료 리스트'}
        topBtnLable={
          userProfile.userTypeCode === UserTypeCode.MASTER_ADMINISTRATOR
            ? '다운로드 '
            : '결제하기'
        }
        onTopButtonClick={
          userProfile.userTypeCode === UserTypeCode.MASTER_ADMINISTRATOR
            ? onDownLoadExcel
            : onSignatureHandler
        }
        headers={headers}
        dataItem={approveDataList.length > 0 ? approveDataList : []}
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
