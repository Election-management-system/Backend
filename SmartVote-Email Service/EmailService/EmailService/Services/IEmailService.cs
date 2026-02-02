using EmailService.DTOs;

namespace EmailService.Services
{
    public interface IEmailService
    {
        void SendEmail(EmailRequestDto request);
    }
}
