import { logger } from '@/store/logger'
import { create } from 'zustand'

interface CountState {
  countState: number
}
interface CountStore extends CountState {
  plusCount: () => void
  initCount: () => void
}
interface uesLoadingStore {
  isLoading: boolean
  onLoading: () => void
  offLoading: () => void
}
const defaultState = { countState: 0 }

// store에서 state 만든 부분.
// 스토어를 만들 때는 create 함수를 이용하여 상태와 그 상태를 변경하는 액션을 정의
export const useCountStore = create<CountStore>()(
  logger<CountStore>(
    (set) => ({
      ...defaultState,
      plusCount: () => {
        set((state) => ({
          countState: state.countState + 1,
        }))
      },
      initCount: () => {
        set(() => ({
          countState: 0,
        }))
      },
    }),
    'countStore'
  )
)

export const useLoadingStore = create<uesLoadingStore>()(
  logger<uesLoadingStore>(
    (set) => ({
      isLoading: false,
      onLoading: () => {
        set(() => ({ isLoading: true }))
      },
      offLoading: () => {
        set(() => ({ isLoading: false }))
      },
    }),
    'loadingStore'
  )
)
interface PathParamStore {
  lastPath: [href: string, options?: any | undefined] | any
  useLastPath: boolean
  setLastPath: (href: string, options?: any | undefined) => void
  resetLastPath: () => void
  setUseLastPath: (b: boolean) => void
}
export const usePathParamStore = create<PathParamStore>()(
  logger<PathParamStore>(
    (set) => ({
      lastPath: [''],
      useLastPath: false,
      setLastPath: (args: any) => {
        if (args) {
          set(() => ({ lastPath: [args] }))
        }
      },
      setUseLastPath: (b: boolean) => {
        //console.log('왜이러죠오')
        set(() => ({ useLastPath: b }))
      },
      resetLastPath: () => set(() => ({ lastPath: [''] })),
    }),
    'usePathParamStore'
  )
)
