'use client'

import styled from 'styled-components'
import { HTMLInputTypeAttribute, useEffect, useState } from 'react'
import { useInput } from '@/hooks/useInput'
import CommonDatePicker from '@/components/common/CommonDatePicker'
import CommonInput from '@/components/common/CommonInput'
import { CommonCheckbox, CommonRadio } from '@/components/common/CustomButton'

interface BulletinBoardMultiSearchBarProps {
  inputType?: HTMLInputTypeAttribute
  placeholder?: string
  htmlFor?: string
  label?: string
  startDate: string
  endDate: string
  searchInput: string
  createdBy: string
  onChangeEndDate: (e: any) => void
  onChangeStartDate: (e: any) => void
  onChangeInput: (e: any) => void
  checked?: boolean
  onChangeChecked: (e: any) => void
  onChangeCreatedBy: (e: any) => void
}

const BulletinBoardMultiSearchBar: React.FC<
  BulletinBoardMultiSearchBarProps
> = ({
  inputType = 'string',
  placeholder,
  htmlFor,
  label,
  searchInput,
  createdBy,
  startDate,
  endDate,
  onChangeStartDate,
  onChangeInput,
  onChangeEndDate,
  checked = false,
  onChangeChecked,
  onChangeCreatedBy,
}) => {
  useEffect(() => {
    if (checked) {
      onChangeCreatedBy('')
    }
  }, [checked])
  return (
    <SearchContainer>
      <div className="search-fields">
        <CommonCheckbox
          checked={checked}
          label={'내가 쓴글'}
          onChange={onChangeChecked}
          $labelPosition={'top'}
        />
        <CommonDatePicker
          value={startDate}
          onChange={onChangeStartDate}
          label={'검색 시작일'}
          type={'date'}
          htmlFor={htmlFor}
          placeholder={placeholder}
        />
        <CommonDatePicker
          value={endDate}
          onChange={onChangeEndDate}
          label={'검색 마지막일'}
          type={'date'}
          htmlFor={htmlFor}
          placeholder={placeholder}
        />
        {/* {!checked ? (
          <CommonInput
            value={createdBy}
            onChange={onChangeCreatedBy}
            label={'작성자 명'}
            type={inputType}
            htmlFor={htmlFor}
            placeholder={'작성자명을 입력해주세요'}
          />
        ) : null} */}

        <CommonInput
          value={searchInput}
          onChange={onChangeInput}
          label={'안전문서 제목'}
          type={inputType}
          htmlFor={htmlFor}
          placeholder={'문서 제목을 입력하세요'}
        />
      </div>
    </SearchContainer>
  )
}

export default BulletinBoardMultiSearchBar

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

    .search-button {
      display: flex;
      align-items: end;
      justify-content: flex-end;
    }
  }
`
