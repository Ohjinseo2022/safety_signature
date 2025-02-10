'use client'

import styled, { css } from 'styled-components'

interface InputProps {
  label: string
  checked: boolean
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
  $labelPosition?: 'top' | 'bottom' | 'left' | 'right' // ✅ 라벨 위치 지정
}

interface RadioProps extends InputProps {
  name: string
  value: string
}

// ✅ 라디오 버튼 컴포넌트
export const CommonRadio = ({
  name,
  value,
  checked,
  onChange,
  label,
  $labelPosition = 'right',
}: RadioProps) => (
  <InputWrapper $labelPosition={$labelPosition}>
    {$labelPosition === 'top' && <Label>{label}</Label>}
    <RadioLabel $checked={checked} $labelPosition={$labelPosition}>
      <input
        type="radio"
        name={name}
        value={value}
        checked={checked}
        onChange={onChange}
      />
      <span className="custom-radio"></span>
      {($labelPosition === 'right' || $labelPosition === 'left') && (
        <Label>{label}</Label>
      )}
    </RadioLabel>
    {$labelPosition === 'bottom' && <Label>{label}</Label>}
  </InputWrapper>
)

// ✅ 체크박스 컴포넌트
export const CommonCheckbox = ({
  checked,
  onChange,
  label,
  $labelPosition = 'right',
}: InputProps) => (
  <InputWrapper $labelPosition={$labelPosition}>
    {$labelPosition === 'top' && <Label>{label}</Label>}
    <CheckboxLabel $checked={checked} $labelPosition={$labelPosition}>
      <input type="checkbox" checked={checked} onChange={onChange} />
      <span className="custom-checkbox"></span>
      {($labelPosition === 'right' || $labelPosition === 'left') && (
        <Label>{label}</Label>
      )}
    </CheckboxLabel>
    {$labelPosition === 'bottom' && <Label>{label}</Label>}
  </InputWrapper>
)

// ✅ 스타일드 컴포넌트 정의
const InputWrapper = styled.div<{ $labelPosition: string }>`
  display: flex;
  align-items: center;
  gap: 10px;
  ${({ $labelPosition }) =>
    $labelPosition === 'top' || $labelPosition === 'bottom'
      ? css`
          flex-direction: column;
        `
      : css`
          flex-direction: row;
        `}
`

const Label = styled.span`
  font-size: 16px;
  color: #e0e0e0;
  cursor: pointer;
`

const RadioLabel = styled.label<{ $checked: boolean; $labelPosition: string }>`
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  transition: color 0.3s ease;
  ${({ $labelPosition }) =>
    $labelPosition === 'left'
      ? css`
          flex-direction: row-reverse;
        `
      : css`
          flex-direction: row;
        `}

  input {
    display: none;
  }

  .custom-radio {
    width: 18px;
    height: 18px;
    border: 2px solid ${({ $checked }) => ($checked ? '#60a5fa' : '#555')};
    border-radius: 50%;
    position: relative;
    transition: border 0.3s ease;
  }

  input:checked + .custom-radio::after {
    content: '';
    width: 10px;
    height: 10px;
    background-color: #60a5fa;
    border-radius: 50%;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
`

const CheckboxLabel = styled.label<{
  $checked: boolean
  $labelPosition: string
}>`
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  transition: color 0.3s ease;
  ${({ $labelPosition }) =>
    $labelPosition === 'left'
      ? css`
          flex-direction: row-reverse;
        `
      : css`
          flex-direction: row;
        `}

  input {
    display: none;
  }

  .custom-checkbox {
    width: 18px;
    height: 18px;
    border: 2px solid ${({ $checked }) => ($checked ? '#60a5fa' : '#555')};
    border-radius: 4px;
    position: relative;
    transition: border 0.3s ease;
  }

  input:checked + .custom-checkbox::after {
    content: '✔';
    font-size: 14px;
    font-weight: bold;
    color: #60a5fa;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
`
