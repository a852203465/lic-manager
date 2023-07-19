const request = axios.create({
  timeout: 60000,
  baseURL: '',
  withCredentials: true,
})

request.interceptors.request.use(
  (config) => {
    if (config && config.headers) {
      config.headers['Content-Type'] = 'application/json'
      config.headers['Access-Control-Allow-Origin'] = '*'

      // if (getStore('ACEMES_TOKEN')) {
      //   config.headers['Authorization'] = getStore('ACEMES_TOKEN') || ''
      // }
    }
    // 判断是否有token 面设置token
    // if (config.method === 'get') {
    //   config.paramsSerializer = function (params) {
    //     return qs.stringify(params, { arrayFormat: 'repeat' })
    //   }
    // }
    return config
  },
  err => Promise.reject(err)
)
request.interceptors.response.use(response => {
  if (response.status === 200) {
    //这里获取token，使用cookie保存token
    // if (response.headers.authorization) {
    //   const token = response.headers.authorization;
    //   setStore('ACEMES_TOKEN', token)
    // }
    if (response.data.code === 401) {
      // message.error(response.data.message)
      // removeAll()
      // resetRouter()
      window.location.href = '/'
      // setStore('Authorization', '')
      // setTimeout(() => {
      //   window.location.href = '/'
      // }, 100)
      return
    }
    if (response.data.code === 0 || response.data.code === 200 || response.config.responseType === 'blob' || !response.data.message) {
      return response.data
    } else {
      // message.error(response.data.message)
      return response.data
    }
  } else {
    // message.error(response.data.message)
    return Promise.reject(response.data.message);
  }
},
  (error) => {
    if (error.response) {
      // if (error.response.status === 401) {
      //   message.destroy();
      //   message.error('登录失效，请重新登录');
      //   // logout();
      // } else {
      //   const tips = error.response.data.message || HTTP_CODE[error.response.status];
      //   tips && message.error(tips);
      // }
      return Promise.reject(error.response);
    } else {
      // message.error('请求超时, 请刷新重试');
      return Promise.reject(new Error('请求超时, 请刷新重试'));
    }

  }
)
function get (url, params = {}, config) {
  return request({ method: 'get', url, params, ...config })
}
function post (url, data = {}, config) {
  return request({ method: 'post', url, data, ...config })
}
function put (url, data = {}, config) {
  return request({ method: 'put', url, data, ...config })
}

