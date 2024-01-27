const baseConfig = {}

if (process.env.NODE_ENV === 'development') {
  baseConfig.server = ''
} else if (process.env.NODE_ENV === 'production') {
  baseConfig.server = ''
}

export default baseConfig
