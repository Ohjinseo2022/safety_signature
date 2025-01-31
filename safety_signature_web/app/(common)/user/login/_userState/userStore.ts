import { logger } from '@/store/logger'
import { create } from 'zustand/react'
import { LoginResponseBase, LoginResponseCode } from '../_userRepository/types'

interface UserProfildStore {
  userProfile: LoginResponseBase
  setProfile: (data: LoginResponseBase) => void
}
export const useUserProfile = create<UserProfildStore>()(
  logger<UserProfildStore>(
    (set) => ({
      userProfile: {
        type: LoginResponseCode.LoginPrevious,
      },
      setProfile: (data) => {
        set(() => ({ userProfile: data }))
      },
      initProfile: () =>
        set(() => ({ userProfile: { type: LoginResponseCode.LoginPrevious } })),
    }),
    'useUserProfile'
  )
)
