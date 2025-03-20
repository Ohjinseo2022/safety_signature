'use client'

import { useEffect, useState } from 'react'
import { useInput } from '@/hooks/useInput'
import CommonInput from '@/components/common/CommonInput'
import CommonModal from '@/components/modal/CommonModal'
import { ApproveMasterType } from '../_hooks/BulletinBoardQuery'

interface ApproveDataModifyModalProps {
  isVisible: boolean
  setIsVisible: (e: any) => void
  item: ApproveMasterType
  onModifyApproveDataHandler: (e: any) => void
}

const ApproveDataModifyModal: React.FC<ApproveDataModifyModalProps> = ({
  isVisible,
  setIsVisible,
  item,
  onModifyApproveDataHandler,
}) => {
  const [data, onChangeData, setData] = useInput<ApproveMasterType>({})
  useEffect(() => {
    setData(item)
  }, [item])
  const callBackFunction = () => {
    onModifyApproveDataHandler?.(data)
  }
  const onChangeHandler = (
    e: any,
    inputType: 'constructionBusiness' | 'companyName'
  ) => {
    if (inputType === 'constructionBusiness') {
      setData((state) => ({ ...state, constructionBusiness: e.target.value }))
    }
    if (inputType === 'companyName') {
      setData((state) => ({
        ...state,
        companyName: e.target.value,
      }))
    }
  }
  return (
    <CommonModal
      isVisible={isVisible}
      setIsVisible={setIsVisible}
      onConfirmText="수정"
      isCancel={true}
      callBackFunction={callBackFunction}
    >
      <>
        <CommonInput
          label="업체명"
          placeholder="업체명을 입력하세요"
          type="text"
          onChange={(e) => onChangeHandler(e, 'companyName')}
          value={data.companyName}
        />
        <CommonInput
          label="공종"
          placeholder="공종을 입력하세요"
          type="text"
          onChange={(e) => onChangeHandler(e, 'constructionBusiness')}
          value={item.constructionBusiness}
        />

        {/*
        TODO : 추후 작업예정 이름 변경시 싸인정보나 다양한 정보의 수정필요함 ! ! 
        <CommonInput
          label="이름"
          placeholder="이름을 입력하세요"
          type="text"
          onChange={(e) => onChangeHandler(e, 'constructionBusiness')}
          value={item.userName}
        /> */}
      </>
    </CommonModal>
  )
}

export default ApproveDataModifyModal
