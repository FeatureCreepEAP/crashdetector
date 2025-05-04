#!/usr/bin/env ruby
# -*- coding: utf-8 -*-

require 'cgi'
require 'time'
require 'fileutils'

# Configuraci?n
DIRECTORIO_INFORMES = File.expand_path('informes')
DOMINIOS_PERMITIDOS = ['securelogger.net', 'curseforge.com', 'discord.gg', 'qq.com']

begin
  FileUtils.mkdir_p(DIRECTORIO_INFORMES) unless File.directory?(DIRECTORIO_INFORMES)

  # Limpiar archivos antiguos
  Dir.glob("#{DIRECTORIO_INFORMES}/*.html").each do |archivo|
    File.delete(archivo) if File.mtime(archivo) < (Time.now - 3 * 86400)
  end

  cgi = CGI.new

  if cgi.request_method == 'POST'
    html_original = cgi.params['html_content']&.first&.force_encoding('UTF-8')
    raise "Contenido HTML no recibido" if html_original.nil? || html_original.empty?

    # Sanitizaci?n mejorada
    html_sanitizado = html_original.dup
    html_sanitizado.gsub!(/<script\b[^>]*>.*?<\/script>/im, '')
    html_sanitizado.gsub!(/on\w+\s*=\s*['"][^'"]*['"]/i, '')

    # Filtrado de dominios
    html_sanitizado.gsub!(%r{<(img|a)\b([^>]*?)(src|href)=["']([^"']+)["']}i) do |match|
      tag = $1.downcase
      attr = $3.downcase
      url = $4
      begin
        uri = URI.parse(url)
        if DOMINIOS_PERMITIDOS.any? { |d| uri.host.to_s.end_with?(d) }
          match
        else
          tag == 'img' ? %(<#{tag} #{attr}="" alt="Imagen bloqueada">) : %(<#{tag} href="#">Enlace bloqueado</#{tag}>)
        end
      rescue URI::InvalidURIError
        tag == 'img' ? %(<#{tag} #{attr}="" alt="URL inv?lida">) : %(<#{tag} href="#">URL inv?lida</#{tag}>)
      end
    end

    # Guardar archivo con el contenido sanitizado
    nombre_archivo = Time.now.utc.strftime("%Y%m%d%H%M%S.html")
    ruta_archivo = File.join(DIRECTORIO_INFORMES, nombre_archivo)
    File.open(ruta_archivo, 'w:utf-8') do |f|
      f.write(html_sanitizado) # Solo escribir el contenido sanitizado
    end

    # Respuesta con URL
    url_nuevo = "https://asbestosstar.egoism.jp/crash_detector/#{File.basename(DIRECTORIO_INFORMES)}/#{CGI.escape(nombre_archivo)}"
    cgi.out('status' => '200 OK', 'Content-Type' => 'text/plain') { url_nuevo }
  else
    cgi.out('status' => '405 Method Not Allowed') { 'M?todo no permitido' }
  end
rescue => e
  puts "Content-Type: text/plain"
  puts "Status: 500 Internal Server Error"
  puts
  puts "Error: #{e.message}"
  puts "Tiempo: #{Time.now.utc}"
end
