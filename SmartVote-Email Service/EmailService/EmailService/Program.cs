using EmailService.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();
builder.Services.AddScoped<IEmailService, EmailService.Services.EmailService>();

var app = builder.Build();

app.MapControllers();
app.Run();
