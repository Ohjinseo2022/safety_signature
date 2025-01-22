'use client'

import styled from 'styled-components'
import { HTMLInputTypeAttribute, useEffect, useState } from 'react'
import { useInput } from '@/hooks/useInput'
import CommonButton from './CommonButton'
import CommonInput from './CommonInput'

interface CommonSearchBarProps {
  type?: 'button' | 'submit' | 'reset' | undefined
  inputType?: HTMLInputTypeAttribute
  placeholder?: string
  htmlFor?: string
  label?: string
  onSubmit: (e: any) => void
}

const CommonSearchBar = ({
  type = 'submit',
  inputType = 'string',
  placeholder,
  htmlFor,
  label,
  onSubmit,
}: CommonSearchBarProps) => {
  const handleSearch = (e: any) => {
    e.preventDefault()
    onSubmit(inputValue)
  }
  const [inputValue, onChangeInput, setInputValue] = useInput('')
  return (
    <SearchContainer>
      <form onSubmit={handleSearch}>
        <div className="search-fields">
          <CommonInput
            value={inputValue}
            onChange={onChangeInput}
            label={label}
            type={inputType}
            htmlFor={htmlFor}
            placeholder={placeholder}
          />
          <div className="search-button">
            <CommonButton type={type}>검색</CommonButton>
          </div>
        </div>
      </form>
    </SearchContainer>
  )
}

export default CommonSearchBar
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
