import { useQuery } from '@tanstack/react-query'
import useFetchApi from '@/hooks/useFetchApi'

interface BulletinBoardSearchProps {
  boardTitle: string
  createdBy: string
  startDate: string
  endDate: string
  isOwner: boolean
  page?: number
  size?: number
}
const getBulletinBoardList = async (props: BulletinBoardSearchProps) => {
  const { data, error, count } = await useFetchApi<{
    data: BulletinBoardMasterType[]
  }>(
    '/bulletin-board/registration/list-for-administrators',
    {
      method: 'get',
      params: props,
    },
    { isAuth: true }
  )
  if (!error && data) {
    console.log(data)
    console.log('count : ', count)
    return { ...data, count: count }
  } else {
    console.log(error)
    return error
  }
}

export const useBulletinBoardListQuery = (props: BulletinBoardSearchProps) => {
  return useQuery({
    queryKey: ['bulletinBoard', props], // 쿼리키에 들어있는 변수의 값들이 바뀔때마다 리패칭 기능 동작함
    // enabled:
    queryFn: async () => getBulletinBoardList(props),
    gcTime: 30 * 60 * 1000, // 30분
  })
}
