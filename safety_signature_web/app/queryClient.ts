import { QueryClient, useMutation } from '@tanstack/react-query'

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 60 * 1000,
    },
  },
})
// const mutation = useMutation()
export default queryClient
