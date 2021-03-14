import request from '@/utils/request'

export default({
    getVideoPlayAuth(videoSourceId){
        return request({
            url:`/vod/getVideoPlayAuth/${videoSourceId}`,
            method: 'get'
        })
    },
})