'use client'

import { useAlertStore } from '@/store/alertStore'
import html2canvas from 'html2canvas'
import { jsPDF } from 'jspdf'
import styled from 'styled-components'
import { use, useEffect, useMemo, useRef, useState } from 'react'
import { useRouter } from 'next/navigation'
import { SafetySignatureStatusCode } from '@/types/enum'
import { getDownloadExcel } from '@/hooks/common/useDownLoad'
import { useInput } from '@/hooks/useInput'
import CommonButton from '@/components/common/CommonButton'
import CommonDataTable from '@/components/common/CommonDataTable'
import CustomDownloadButton from '@/components/custom/CustomDownloadButton'
import EducationCertificate from '@/components/custom/EducationCertificate'
import CommonModal from '@/components/modal/CommonModal'
import { CommonCard } from '@/components/styled/common'
import {
  LoginResponseSuccess,
  UserTypeCode,
} from '@/app/(common)/user/login/_userRepository/types'
import { useUserProfile } from '@/app/(common)/user/login/_userState/userStore'
import {
  ApproveMasterType,
  approveSignature,
  onBulletinUpdateOrNewBulletin,
  useApproveListQuery,
  useBulletinBoardQuery,
} from '../../_hooks/BulletinBoardQuery'

interface BulletinDetailPageProps {
  params: Promise<{ id: string }>
}

const BulletinDetailPage = ({ params }: BulletinDetailPageProps) => {
  const approveTable = useRef<HTMLElement>(null)
  const unwrappedParams = use(params) // params를 언래핑
  const router = useRouter()
  const { isModalVisible, onChangeModalVisible, callBackFunction } =
    useAlertStore()
  const [bulletinModal, onChnageBulletinModal, setBulletinModal] = useInput<{
    isVisible: boolean
    children: string
    callBack: () => void
  }>({
    isVisible: false,
    children: '',
    callBack: () => router.push('/bulletin'),
  })
  const userProfile = useUserProfile().userProfile as LoginResponseSuccess
  const {
    data: bulletinBoardDetail,
    error,
    isFetched,
    refetch,
  } = useBulletinBoardQuery(unwrappedParams.id)
  const [page, onChangePage, setPage] = useInput<number>(1)
  // 결재 완료 리스트
  const {
    data: approveList,
    error: approveListError,
    isFetched: approveListIsFetched,
    refetch: approveListRefetch,
  } = useApproveListQuery({
    bulletinBoardId: unwrappedParams.id,
    page: page - 1,
    size: 23,
  })
  const detailData: BulletinBoardMasterType = useMemo(() => {
    if (isFetched && bulletinBoardDetail) {
      return bulletinBoardDetail.data
    }
    return undefined
  }, [isFetched, bulletinBoardDetail])

  const approveDataList: {
    evenData: ApproveMasterType[][]
    oddData: ApproveMasterType[][]
  } = useMemo(() => {
    if (approveListIsFetched && approveList) {
      return approveList
    }
    return []
  }, [approveList, approveListIsFetched])
  //console.log('unwrappedParams : ', unwrappedParams)
  const headers = [
    { label: 'NO', columns: 'index' },
    { label: '업체명', columns: 'companyName' },
    { label: '공종', columns: 'constructionBusiness' },
    { label: '성명', columns: 'userName' },
    { label: '확인', columns: 'attachDocId' },
  ]

  const onDownLoadExcel = async () => {
    // await getDownloadExcel(unwrappedParams.id)
    if (approveTable.current) {
      const element = approveTable.current
      element.style.fontSize = '16px' // 강제 적용
      element.style.textAlign = 'center'
      const canvas = await html2canvas(element, {
        scale: 2, // 해상도를 높여서 캡처
        useCORS: true, // CORS 문제 해결
        allowTaint: true, // 외부 리소스 허용
        backgroundColor: null, // 배경 투명
        logging: false, // 콘솔 로그 비활성화
        removeContainer: true, // 캡처 후 DOM에서 임시 컨테이너 삭제
      })
      const imgData = canvas.toDataURL('image/png')

      const componentWidth = element.offsetWidth
      const componentHeight = element.offsetHeight

      const pdf = new jsPDF({
        orientation: 'p', // 기본 A4 세로
        unit: 'mm', //픽셀단위로
        format: 'a4',
      })

      // A4 크기
      const A4_WIDTH = 210 // mm
      const A4_HEIGHT = 297 // mm
      const scaleFactor = A4_WIDTH / componentWidth
      const scaledHeight = componentHeight * scaleFactor

      if (scaledHeight < A4_HEIGHT) {
        // 요소가 A4보다 작다면, 공백을 추가
        pdf.addImage(imgData, 'PNG', 0, 0, A4_WIDTH, scaledHeight)
        pdf.setFillColor(255, 255, 255) // 흰색
        pdf.rect(0, scaledHeight, A4_WIDTH, A4_HEIGHT - scaledHeight, 'F') // 나머지 영역 채우기
      } else {
        // 요소가 A4보다 크다면, 여러 페이지로 나눔
        let yPosition = 0
        while (yPosition < componentHeight) {
          pdf.addImage(
            imgData,
            'PNG',
            0,
            -yPosition * scaleFactor,
            A4_WIDTH,
            componentHeight * scaleFactor
          )
          yPosition += A4_HEIGHT / scaleFactor
          if (yPosition < componentHeight) pdf.addPage()
        }
      }

      pdf.save(`${detailData.boardTitle}.pdf`)
    }
  }
  const onSignatureHandler = async () => {
    const result = await approveSignature(unwrappedParams.id)

    if (result) {
      onChangeModalVisible({ isVisible: true, msg: result.message })
      result.status === 200 ? approveListRefetch() : undefined
    }
  }
  const onStatusChangeHandler = async (type: 'D' | 'A') => {
    onChangeModalVisible({
      isVisible: true,
      isCancel: true,
      msg:
        type === 'D'
          ? '정말 삭제 하시겠습니까?'
          : '문서를 다시 복구 시키시겠습니까?',
      callBackFunction: async () => {
        const result = await onBulletinUpdateOrNewBulletin({
          bulletinBoardId: unwrappedParams.id,
          statusCode:
            type === 'D'
              ? SafetySignatureStatusCode.DELETED
              : SafetySignatureStatusCode.PUBLISHED,
        })
        if (result) {
          setBulletinModal({
            isVisible: result,
            children: `전자결재 문서가 ${type === 'D' ? '삭제' : '복구'}가 완료됐습니다.`,
            callBack:
              type === 'D' ? () => router.push('/bulletin') : () => refetch(),
          })
        }
      },
    })
  }

  return detailData ? (
    <DetailContainer>
      <PostInfo>
        <FlexTitle>
          <h2>{detailData.boardTitle}</h2>
          {userProfile.id === detailData.userMasterId && (
            <div>
              <CommonButton>수정</CommonButton>
              <CommonButton
                $backgroundColor={'#FF0000'}
                onClick={() =>
                  onStatusChangeHandler(
                    detailData.boardStatusCode !==
                      SafetySignatureStatusCode.DELETED
                      ? 'D'
                      : 'A'
                  )
                }
              >
                {detailData.boardStatusCode !==
                SafetySignatureStatusCode.DELETED
                  ? '삭제'
                  : '복구'}
              </CommonButton>
            </div>
          )}
        </FlexTitle>
        <div
          className="content"
          dangerouslySetInnerHTML={{ __html: detailData.boardContents }}
        ></div>
      </PostInfo>
      {(detailData.siteAddress || detailData.siteName) && (
        <CommonCard>
          <h3>현장 정보</h3>
          <div style={{ height: `40px`, padding: '10px 16px' }}>
            {`${detailData.siteName ?? detailData.siteAddress}`}
          </div>
        </CommonCard>
      )}
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
        title={'결재완료 리스트'}
        topBtnLable={
          userProfile.id === detailData.userMasterId
            ? '다운로드 '
            : !detailData.completionYn
              ? '결재하기'
              : ''
        }
        onTopButtonClick={
          userProfile.id === detailData.userMasterId
            ? onDownLoadExcel
            : onSignatureHandler
        }
        children={
          <EducationCertificate
            ref={approveTable}
            siteName={detailData.siteName}
            educationDate={detailData.createdDateFormat}
            educationType={detailData.boardTitle}
            headers={headers}
            participants={approveDataList}
          />
        }
        dataItem={[]}
        headers={headers}
      />

      <CommonModal
        isVisible={bulletinModal.isVisible}
        setIsVisible={(e: boolean) =>
          setBulletinModal((state) => {
            return { ...state, isVisible: e }
          })
        }
        callBackFunction={bulletinModal.callBack}
      >
        {bulletinModal.children}
      </CommonModal>
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
const FlexTitle = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
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
