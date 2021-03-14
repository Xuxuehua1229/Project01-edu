import request from '@/utils/request'

export default({
    //获取前台所有讲师信息
    getFrontTeacherInfo(currentPage,pageSize){
        return request({
            //url: '/edu/teacher/front/' + currentPage + "/" + pageSize,
            url:`/edu/frontTeacher/${currentPage}/${pageSize}`,
            method: 'get'
        })
    },
    //根据讲师id获取讲师信息及对应所讲课程
    getTeacherAndCourseInfo(id){
        return request({
            url:`/edu/frontTeacher/getFrontTeacherDetails/${id}`,
            method: 'get'
        })
    }
})