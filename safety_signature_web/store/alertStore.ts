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
  onChangeModalVisible: ({
    isVisible,
    msg,
    callBackFunction,
  }: {
    isVisible: boolean
    msg?: string
    callBackFunction?: () => boolean
    overlayClose?: boolean
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
      onChangeModalVisible: ({
        isVisible,
        msg = '',
        callBackFunction,
        overlayClose = true,
      }) => {
        set(() => ({
          isModalVisible: isVisible,
          children: msg,
          callBackFunction: callBackFunction,
          overlayClose: overlayClose,
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
