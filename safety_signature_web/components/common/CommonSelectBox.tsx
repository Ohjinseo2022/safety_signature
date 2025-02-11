'use client'

import styled from 'styled-components'
import { useEffect, useState } from 'react'
import { useInput } from '@/hooks/useInput'

interface CommonSelectBoxProps {
  label?: string
  initialValue: string | number | readonly string[] | undefined
  options: {
    value: string | number | readonly string[] | undefined
    label: string
  }[]
  onChange?: (e: any) => void
}

const CommonSelectBox = ({
  initialValue,
  label,
  options,
  onChange,
}: CommonSelectBoxProps) => {
  const [selectValue, onSelectChange, setSelectValue] =
    useInput<any>(initialValue)
  const onOptionChangeHandler = (e: any) => {
    if (typeof onChange === 'function') {
      onChange(e)
    }
    onSelectChange(e)
  }
  return (
    <div className="search-field">
      {label ? <Label>{label}</Label> : null}
      <Select
        id="newStatus"
        value={selectValue}
        onChange={onOptionChangeHandler}
      >
        {options.map((item, idx) => (
          <option value={item.value} key={`${item.value}-${idx}`}>
            {item.label}
          </option>
        ))}
      </Select>
    </div>
  )
}

export default CommonSelectBox
const Select = styled.select`
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #444444;
  background-color: #2a2a2a;
  color: #e0e0e0;
  font-size: 14px;
  height: 40px;
`
const Label = styled.label`
  margin-bottom: 8px;
  color: #b0b0b0;
  font-size: 14px;
`
