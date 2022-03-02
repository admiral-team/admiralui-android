require 'json'

# Version management

def current_lib_external_version
  file_path = version_file_path
  params = JSON.parse(File.read(file_path))
  params["external_version"]
end

def current_lib_internal_version
  file_path = version_file_path
  params = JSON.parse(File.read(file_path))
  params["internal_version"]
end

def current_lib_version
  "Android #{current_lib_full_version}"
end

def unreleased_version
  "Android UNRELEASED (#{current_lib_internal_version})"
end

def current_lib_full_version
  version_name = current_lib_external_version + " (" + current_lib_internal_version + ")"
  version_name
end

def last_version_tag
  system('git describe --tags `git rev-list --tags --max-count=1`')
end

def set_version(external_version:, internal_version:)
  file_path = version_file_path
  params = JSON.parse(File.read(file_path))
  params['internal_version'] = internal_version if !internal_version.nil?
  params['external_version'] = external_version if !external_version.nil?

  File.open(file_path, "w") {|f| f.write(JSON.pretty_generate(params)) }
end

def version_file_path
    File.join(Dir.pwd, "../#{ ENV['VERSION_FILE'] }")
end