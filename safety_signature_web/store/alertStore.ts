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
    isCancel,
    callBackFunction,
  }: {
    isVisible: boolean
    msg?: string
    callBackFunction?: () => boolean | Promise<any> | void
    isCancel?: boolean
    overlayClose?: boolean
  }) => void
  callBackFunction?: (e: any) => boolean | Promise<any> | void
  children: ReactNode | string
  initAlertStore: () => void
}
export const useAlertStore = create<AlertStoreType>()(
  logger<AlertStoreType>(
    (set) => ({
      isModalVisible: false,
      overlayClose: true,
      title: undefined,
      isCancel: false,
      children: '',
      onChangeModalVisible: ({
        isVisible,
        msg = '',
        callBackFunction,
        isCancel = false,
        overlayClose = true,
      }) => {
        set(() => ({
          isModalVisible: isVisible,
          children: msg,
          callBackFunction: callBackFunction,
          isCancel: isCancel,
          overlayClose: overlayClose,
        }))
      },
      initAlertStore: () => {
        set(() => ({
          isModalVisible: false,
          overlayClose: true,
          title: undefined,
          children: '',
          callBackFunction: (e: any) => undefined,
        }))
      },
    }),
    'useAlertStore'
  )
)
