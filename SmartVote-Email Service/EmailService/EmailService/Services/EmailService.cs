using System.Net;
using System.Net.Mail;
using EmailService.DTOs;

namespace EmailService.Services
{
    public class EmailService : IEmailService
    {
        private readonly IConfiguration _config;

        public EmailService(IConfiguration config)
        {
            _config = config;
        }

        public void SendEmail(EmailRequestDto request)
        {
            var smtpClient = new SmtpClient
            {
                Host = _config["EmailSettings:SmtpHost"],
                Port = int.Parse(_config["EmailSettings:SmtpPort"]),
                EnableSsl = true,
                Credentials = new NetworkCredential(
                    _config["EmailSettings:Username"],
                    _config["EmailSettings:Password"]
                )
            };

            var mailMessage = new MailMessage
            {
                From = new MailAddress(_config["EmailSettings:From"]),
                Subject = request.Subject,
                Body = request.Body,
                IsBodyHtml = true
            };

            mailMessage.To.Add(request.To);

            smtpClient.Send(mailMessage);
        }
    }
}
