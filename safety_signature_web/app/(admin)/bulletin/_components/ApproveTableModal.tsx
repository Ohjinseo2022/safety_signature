'use client'

import html2canvas from 'html2canvas'
import jsPDF from 'jspdf'
import { RefObject, useEffect, useRef, useState } from 'react'
import { getDownloadExcel } from '@/hooks/common/useDownLoad'
import EducationCertificate from '@/components/custom/EducationCertificate'
import CommonModal from '@/components/modal/CommonModal'
import { ApproveMasterType } from '../_hooks/BulletinBoardQuery'

interface ApproveTableModalProps {
  isVisible: boolean
  setIsVisible: (e: any) => void
  siteName: string
  educationDate: string
  educationType: string
  participants: {
    evenData: ApproveMasterType[][]
    oddData: ApproveMasterType[][]
  }
}

const ApproveTableModal: React.FC<ApproveTableModalProps> = ({
  isVisible,
  setIsVisible,
  siteName,
  educationDate,
  educationType,
  participants,
}) => {
  const headers = [
    { label: 'NO', columns: 'index' },
    { label: '업체명', columns: 'companyName' },
    { label: '공종', columns: 'constructionBusiness' },
    { label: '성명', columns: 'userName' },
    { label: '확인', columns: 'attachDocId' },
  ]
  const approveTable = useRef<HTMLElement>(null)
  const download = async () => {
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

      pdf.save(`${educationDate}.pdf`)
    }
  }
  return (
    <CommonModal
      isVisible={isVisible}
      setIsVisible={setIsVisible}
      width="100%"
      height="100%"
      onConfirmText="다운로드"
      callBackFunction={download}
    >
      <EducationCertificate
        ref={approveTable}
        siteName={siteName}
        educationDate={educationDate}
        educationType={educationType}
        headers={headers}
        participants={participants}
      />
    </CommonModal>
  )
}

export default ApproveTableModal
