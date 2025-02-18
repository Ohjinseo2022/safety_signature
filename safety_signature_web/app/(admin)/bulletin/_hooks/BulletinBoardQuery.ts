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
    queryKey: ['bulletinBoardList', props], // 쿼리키에 들어있는 변수의 값들이 바뀔때마다 리패칭 기능 동작함
    // enabled: false
    queryFn: async () => getBulletinBoardList(props),
    gcTime: 30 * 60 * 1000, // 30분
  })
}
//단건 조회
const getBulletinBoard = async (id: string) => {
  const { data, error, count } = await useFetchApi<{
    data: BulletinBoardMasterType
  }>(`/bulletin-board/registration/${id}`, { method: 'get' }, { isAuth: true })
  if (!error && data) {
    return { ...data }
  } else {
    return error
  }
}
export const useBulletinBoardQuery = (props: string) => {
  return useQuery({
    queryKey: ['bulletinBoardOne', props],
    queryFn: async () => getBulletinBoard(props),
    gcTime: 30 * 60 * 1000,
  })
}
