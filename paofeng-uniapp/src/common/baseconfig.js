const baseconfig = {}

if (process.env.NODE_ENV === 'development') {
  baseconfig.server = ''
} else if (process.env.NODE_ENV === 'production') {
  baseconfig.server = ''
}

export default baseconfig
