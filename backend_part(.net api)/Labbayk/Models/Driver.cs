using System.ComponentModel.DataAnnotations;

namespace Labbayk.Models
{
    public class Driver
    {
        [Key]
        public int Id { get; set; }
        public string Name { get; set; }
        public ICollection<Order> Orders { get; set; }
    }
}
