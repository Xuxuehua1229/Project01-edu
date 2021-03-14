import request from '@/utils/request'

export default({
    //获取前台所有课程信息
    getFrontCourseInfo(currentPage,pageSize){
        return request({
            //url: '/edu/teacher/front/' + currentPage + "/" + pageSize,
            url:`/edu/courseFront/getCourseInfoPage/${currentPage}/${pageSize}`,
            method: 'get'
        })
    },
    //根据课程id获取课程信息及对应所讲课程的所有章节以及小节
    getCourseAllInfo(id){
        return request({
            url:`/edu/courseFront/getCourseInfoAll/${id}`,
            method: 'get'
        })
    }
})