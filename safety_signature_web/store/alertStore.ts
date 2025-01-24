import { create } from 'zustand'
import { ReactNode } from 'react'
import { logger } from './logger'

interface AlertStoreType {
  isModalVisible: boolean
  overlayClose?: boolean
  isCancel?: boolean
  title?: string
  width?: string
  height?: string
  onChangeModelVisible: ({
    isVisible,
    msg,
  }: {
    isVisible: boolean
    msg?: string
  }) => void
  children: ReactNode | string
}
export const useAlertStore = create<AlertStoreType>()(
  logger<AlertStoreType>(
    (set) => ({
      isModalVisible: false,
      children: '',
      onChangeModelVisible: ({ isVisible, msg = '' }) => {
        set(() => ({
          isModalVisible: isVisible,
          children: msg,
        }))
      },
    }),
    'useAlertStore'
  )
)
