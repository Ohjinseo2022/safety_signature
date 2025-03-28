'use client'

import styled from 'styled-components'
import { HTMLInputTypeAttribute, useEffect, useState } from 'react'

interface CommonDatePickerProps {
  type?: HTMLInputTypeAttribute
  placeholder?: string
  htmlFor?: string
  value: any
  onChange: (e: any) => void
  label?: string
  readOnly?: boolean
  onKeydown?: (e: any) => void
}

const CommonDatePicker: React.FC<CommonDatePickerProps> = ({
  type = 'date',
  placeholder,
  htmlFor,
  value,
  label,
  readOnly,
  onChange,
}) => {
  const onDateChange = (e: any) => {
    onChange(e)
  }
  return (
    <DefaultInputField>
      {label ? <Label htmlFor={htmlFor}>{label}</Label> : null}
      <Input
        id={htmlFor}
        placeholder={placeholder}
        type={type}
        value={value}
        onChange={onDateChange}
        readOnly={readOnly}
        onKeyDown={(e) => e.preventDefault()}
      ></Input>
    </DefaultInputField>
  )
}

export default CommonDatePicker
const Input = styled.input`
  padding: 10px;
  border-radius: 4px;
  border: 1px solid #444444;
  background-color: #2a2a2a;
  color: #e0e0e0;
  font-size: 14px;
  height: 40px;

  &::placeholder {
    color: #666666;
  }
`

const Label = styled.label`
  margin-bottom: 8px;
  color: #b0b0b0;
  font-size: 14px;
`
const DefaultInputField = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
  min-width: 200px;
`
