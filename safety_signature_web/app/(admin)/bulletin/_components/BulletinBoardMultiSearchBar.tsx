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
  onChangeEndDate: (e: any) => void
  onChangeStartDate: (e: any) => void
  onChangeInput: (e: any) => void
  onSubmit: (e: any) => void
  checked?: boolean
  onChangeChecked: (e: any) => void
}

const BulletinBoardMultiSearchBar: React.FC<
  BulletinBoardMultiSearchBarProps
> = ({
  inputType = 'string',
  placeholder,
  htmlFor,
  label,
  searchInput,
  startDate,
  endDate,
  onChangeStartDate,
  onChangeInput,
  onChangeEndDate,
  checked = false,
  onChangeChecked,
}) => {
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
        <CommonInput
          value={searchInput}
          onChange={onChangeInput}
          label={label}
          type={inputType}
          htmlFor={htmlFor}
          placeholder={placeholder}
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
