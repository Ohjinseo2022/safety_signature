import { SyntheticEvent, useCallback, useState } from 'react'

/**
 * @param initialValue
 * @param validation
 * @returns initialValue -> 입력받는 value , validation -> value 체크로직 , 리턴은 무조건 boolean 방식으로 함수선언
 */
const useInput = (initialValue: any, validation?: (e: any) => {}) => {
  const [value, setValue] = useState(initialValue)
  const onChange = useCallback((e: any) => {
    let willUpdate: boolean = true
    if (typeof validation === 'function') {
      const tempValue = !(typeof e === 'object') ? e : e.target.value
      willUpdate = validation(tempValue) as boolean
    }
    if (willUpdate) {
      setValue(!(typeof e === 'object') ? e : e.target.value)
    }
  }, [])
  return [value, onChange, setValue]
}

export { useInput }
