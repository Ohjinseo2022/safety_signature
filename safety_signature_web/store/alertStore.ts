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
  callBackFunction?: (e: any) => boolean
  children: ReactNode | string
  initAlertStore: () => void
}
export const useAlertStore = create<AlertStoreType>()(
  logger<AlertStoreType>(
    (set) => ({
      isModalVisible: false,
      overlayClose: true,
      title: undefined,
      children: '',
      onChangeModelVisible: ({ isVisible, msg = '' }) => {
        set(() => ({
          isModalVisible: isVisible,
          children: msg,
        }))
      },
      initAlertStore: () => {
        set(() => ({
          isModalVisible: false,
          overlayClose: true,
          title: undefined,
          children: '',
          callBackFunction: (e: any) => true,
        }))
      },
    }),
    'useAlertStore'
  )
)
