# BuildInfo management

class BuildInfo
  attr_accessor :platform
  attr_accessor :build_id
  attr_accessor :version
  attr_accessor :internal_version
  attr_accessor :short_version
  attr_accessor :download_url
  attr_accessor :install_url
  attr_accessor :build_url
  attr_accessor :branch_name
  attr_accessor :issue

  def initialize()
    @platform = 'Android'
  end

  def self.from_hash(hash)
    hash = hash || Hash.new
    info = BuildInfo.new
    info.build_id = hash['id']
    info.version = hash['version']
    info.short_version = hash['short_version']
    info.download_url = hash['download_url']
    info.install_url = hash['install_url']
    info
  end
end

def generate_random_build_number
  Time.now.to_i
end

def appcenter_build_dev_url(build_id:)
  return nil if build_id.nil?
  base_url = ENV['APPCENTER_BASE_URL']
  owner_name = ENV['APPCENTER_OWNER_NAME']
  app_name = ENV['APPCENTER_APP_NAME_DEV']
  File.join(base_url, 'orgs', owner_name, 'apps', app_name, 'releases', "#{build_id}")
end

def appcenter_build_prod_url
  base_url = ENV['APPCENTER_BASE_URL']
  owner_name = ENV['APPCENTER_OWNER_NAME']
  app_name = ENV['APPCENTER_APP_NAME']
  group = ENV['APPCENTER_DESTINATIONS_PROD']
  File.join(base_url, 'orgs', owner_name, 'apps', app_name, 'distribution_groups', group)
end

def formatted_build_info_telegram(build_info:)
  text = ""

  unless build_info.nil?
    text += "<strong>Platform:</strong> #{build_info.platform}\n" unless build_info.platform.nil?
    text += "<strong>Build ID:</strong> #{build_info.build_id}\n" unless build_info.build_id.nil?
    text += "<strong>Version:</strong> #{build_info.version}\n" unless build_info.version.nil?
    text += "<strong>Internal Version:</strong> #{build_info.internal_version}\n" unless build_info.internal_version.nil?
    text += "<strong>Short Version:</strong> #{build_info.short_version}\n" unless build_info.short_version.nil?
    text += "<strong>Branch Name:</strong> #{build_info.branch_name}\n" unless build_info.branch_name.nil?
    text += "<strong>Issue:</strong> <a href=\"#{jira_issue_link(key: build_info.issue)}\">#{build_info.issue}</a>\n" unless build_info.issue.nil?
    url_buttons = { "Install URL": build_info.build_url } unless build_info.build_url.nil?
  end
  { "text" => text, "url_buttons" => url_buttons }
end

def formatted_build_info(build_info:)
  result_string = ""
  result_string += "Platform: #{build_info.platform}\n" unless build_info.platform.nil?
  result_string += "Build ID: #{build_info.build_id}\n" unless build_info.build_id.nil?
  result_string += "Version: #{build_info.version}\n" unless build_info.version.nil?
  result_string += "Internal Version: #{build_info.internal_version}\n" unless build_info.internal_version.nil?
  result_string += "Short Version: #{build_info.short_version}\n" unless build_info.short_version.nil?
  result_string += "Branch Name: #{build_info.branch_name}\n" unless build_info.branch_name.nil?
  result_string += "*Install URL* [URL|#{build_info.build_url}]" unless build_info.build_url.nil?
  result_string.strip
end

def formatted_build_info_jira(build_info:)
  result_string = ""
  result_string += "Platform: #{build_info.platform}\n" unless build_info.platform.nil?
  result_string += "*Build ID:* #{build_info.build_id}\n" unless build_info.build_id.nil?
  result_string += "*Version:* #{build_info.version}\n" unless build_info.version.nil?
  result_string += "*Internal Version:* #{build_info.internal_version}\n" unless build_info.internal_version.nil?
  result_string += "*Short Version:* #{build_info.short_version}\n" unless build_info.short_version.nil?
  result_string += "*Branch Name:* #{build_info.branch_name}\n" unless build_info.branch_name.nil?
  result_string += "*Install URL* [URL|#{build_info.build_url}]" unless build_info.build_url.nil?
  result_string.strip
end